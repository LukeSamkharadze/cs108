package Bank;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
    private static final int INIT_AMOUNT = 1000;
    private static final int ACCOUNTS = 20;	 // number of accounts



    // Helper function for making transactions
    private void makeTransactions(Transaction transaction) {
        int fromWhere = transaction.fromWhere();
        int toWhere = transaction.toWhere();
        int whatAmount = transaction.whatAmount();

        accs.get(fromWhere).withdraw(whatAmount);
        accs.get(toWhere).deposit(whatAmount);
    }



    /*
     Reads transaction data (from/to/amt) from a file for processing.
     (provided code)
     */
    public void readFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Use stream tokenizer to get successive words from file
            StreamTokenizer tokenizer = new StreamTokenizer(reader);

            while (true) {
                int read = tokenizer.nextToken();
                if (read == StreamTokenizer.TT_EOF) {
                    transactions.add(nullTransaction);
                    break;  // detect EOF
                }
                int from = (int)tokenizer.nval;

                tokenizer.nextToken();
                int to = (int)tokenizer.nval;

                tokenizer.nextToken();
                int amount = (int)tokenizer.nval;

                transactions.put(new Transaction(from, to, amount));
            }
        }
        catch (Exception e) { e.printStackTrace(); System.exit(1); } // just to cover this line
    }



    /*
     Processes one file of transaction data
     -fork off workers
     -read file into the buffer
     -wait for the workers to finish
    */
    public void processFile(String file, int numWorkers) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(numWorkers);
        transactions = new ArrayBlockingQueue<>(500);

        for (int i = 0; i < numWorkers; i++) (new Worker(latch)).start();
        accs = new ArrayList<>();

        for (int i = 0; i < ACCOUNTS; i++) accs.add(new Account(i, INIT_AMOUNT));
        readFile(file);
        latch.await();
        for (int i = 0; i < ACCOUNTS; i++)
            System.out.println(accs.get(i).toString());
    }



    private BlockingQueue<Transaction> transactions;
    private ArrayList<Account> accs;
    private static final Transaction nullTransaction = new Transaction(-1, 0, 0);


    protected class Worker extends Thread {
        protected CountDownLatch latch;

        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run()  {
            while (true) {
                Transaction tr = null;
                try {
                    tr = transactions.take();
                } catch (InterruptedException e) { e.printStackTrace(); }
                if (tr == nullTransaction) {
                    transactions.add(nullTransaction);
                    latch.countDown();
                    break;
                }
                assert tr != null;
                makeTransactions(tr);
            }
        }
    }

    private static final int NUM_T = 1;


    /*
     Looks at commandline args and calls Bank processing.
    */
    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            System.out.println("Args: transaction-file [num-workers [limit]]");
        }else if (args.length == 1) {
            (new Bank()).processFile(args[0], NUM_T);
        }else if (args.length == 2) {
            (new Bank()).processFile(args[0], Integer.parseInt(args[1]));
        }else {
            System.out.println("Too many arguments");
        }

    }
}