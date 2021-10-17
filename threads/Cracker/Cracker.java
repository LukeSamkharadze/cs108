package Cracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Cracker {
    protected static class Worker extends Thread {
        private final int beginIndex, endIndex, length;
        protected String hash;

        public Worker(int beginIndex, int endIndex, int maximumLength, String hash) {
            this.length = maximumLength;
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
            this.hash = hash;
        }

        private void recSearch(String data) throws NoSuchAlgorithmException {
            if (data.length() <= this.length) {
                if (getHashCode(data).equals(this.hash)) results.add(data);
                for (char c : CHARS)
                    if (!isInterrupted()) recSearch(data + c);
                    else break;
            }
        }

        @Override
        public void run() {
            for (int i = beginIndex; i <= endIndex; i++)
                try {
                    String curWord = "" + CHARS[i];
                    recSearch(curWord);
                } catch (NoSuchAlgorithmException e) { }
            countDownLatch.countDown();
        }
    }

    public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
    public static final ArrayList<String> results = new ArrayList<>();
    public static final String HASH = "66b27417d37e024c46526c2f6d358a754fc552f3"; // default Hash
    public static final int NUM_T = 1;
    public static CountDownLatch countDownLatch;

    public static String hexToString(byte[] bytes) {
        var stringBuffer = new StringBuffer();
        for (int aByte : bytes) {
            int val = aByte;
            val = val & 0xff;
            if (val < 16) stringBuffer.append('0');
            stringBuffer.append(Integer.toString(val, 16));
        }
        return stringBuffer.toString();
    }

    public static byte[] hexToArray(String hex) {
        byte[] result = new byte[hex.length() / 2];

        for (int i = 0; i < hex.length(); i += 2) result[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);

        return result;
    }

    private static String getHashCode(String word) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(word.getBytes());
        return hexToString(md.digest());
    }

    private static void cracking(String hash, int length, int numThreads) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        countDownLatch = new CountDownLatch(numThreads);
        int skip = CHARS.length / numThreads;

        for (int i = 0; i < numThreads; i++)
            if (i == numThreads - 1)
                threads.add(new Worker(i * skip, CHARS.length - 1, length, hash));
            else threads.add(new Worker(i * skip, (i + 1) * skip - 1, length, hash));

        for (Thread tid : threads) tid.start();
        countDownLatch.await();
        for (Thread tid : threads) tid.interrupt();
        for (Thread tid : threads) tid.join();
    }

    private static void crackAndPrint(String hash, int length, int numThreads) throws InterruptedException {
        cracking(hash, length, numThreads);
        System.out.println("Found next passwords: ");
        for (String word : results) System.out.println(word);
        System.out.println("ALL DONE.");
    }

    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
        if (args.length < 2) if (args.length == 0) {
            System.out.println("No arguments given, started basic threading.");
            crackAndPrint(HASH, 3, NUM_T);
        } else System.out.println(getHashCode(args[0]));
        else if (args.length == 2) crackAndPrint(args[0], Integer.parseInt(args[1]), NUM_T);
        else if (args.length == 3) crackAndPrint(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        else System.out.println("too many arguments!");
    }
}