package Bank;// Bank.Bank.java

public class Account {
    private int id;
    private int balance;
    private int transactions;


    public Account(int id, int balance) {
        this.id = id;
        this.balance = balance;
        transactions = 0;
    }

    public synchronized void deposit(int amt) {
        balance += amt;
        (transactions)++;
    }

    public synchronized void withdraw(int amt) {
        balance -= amt;
        (transactions)++;
    }

    public synchronized String toString() {
        return "acct:" + id +
                " bal:" + balance +
                " trans:" + transactions;
    }
}