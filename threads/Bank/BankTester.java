package Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static Bank.Bank.*;
import static java.lang.System.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTester {
    private OutputStream outputStream;
    private PrintStream printStream;

    @BeforeEach()
    public void init() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        setOut(printStream);
    }

    @Test
    public void testBank() throws InterruptedException {
        main(new String[]{}); // test empty
        assertEquals("Args: transaction-file [num-workers [limit]]\r\n", outputStream.toString());
    }

    @Test
    public void testBank1() throws InterruptedException {
        main(new String[]{"Bank/small.txt", "bla", "blu", "bli"});

        assertEquals("Too many arguments\r\n", outputStream.toString());
    }

    @Test
    public void testBank2() throws InterruptedException {
        main(new String[]{"Bank/small.txt"});

        assertEquals("acct:0 bal:999 trans:1\r\n" +
                        "acct:1 bal:1001 trans:1\r\n" +
                        "acct:2 bal:999 trans:1\r\n" +
                        "acct:3 bal:1001 trans:1\r\n" +
                        "acct:4 bal:999 trans:1\r\n" +
                        "acct:5 bal:1001 trans:1\r\n" +
                        "acct:6 bal:999 trans:1\r\n" +
                        "acct:7 bal:1001 trans:1\r\n" +
                        "acct:8 bal:999 trans:1\r\n" +
                        "acct:9 bal:1001 trans:1\r\n" +
                        "acct:10 bal:999 trans:1\r\n" +
                        "acct:11 bal:1001 trans:1\r\n" +
                        "acct:12 bal:999 trans:1\r\n" +
                        "acct:13 bal:1001 trans:1\r\n" +
                        "acct:14 bal:999 trans:1\r\n" +
                        "acct:15 bal:1001 trans:1\r\n" +
                        "acct:16 bal:999 trans:1\r\n" +
                        "acct:17 bal:1001 trans:1\r\n" +
                        "acct:18 bal:999 trans:1\r\n" +
                        "acct:19 bal:1001 trans:1\r\n",
                outputStream.toString());
    }

    @Test
    public void testBank3() throws InterruptedException {
        main(new String[]{"Bank/small.txt", "3"});

        assertEquals("acct:0 bal:999 trans:1\r\n" +
                        "acct:1 bal:1001 trans:1\r\n" +
                        "acct:2 bal:999 trans:1\r\n" +
                        "acct:3 bal:1001 trans:1\r\n" +
                        "acct:4 bal:999 trans:1\r\n" +
                        "acct:5 bal:1001 trans:1\r\n" +
                        "acct:6 bal:999 trans:1\r\n" +
                        "acct:7 bal:1001 trans:1\r\n" +
                        "acct:8 bal:999 trans:1\r\n" +
                        "acct:9 bal:1001 trans:1\r\n" +
                        "acct:10 bal:999 trans:1\r\n" +
                        "acct:11 bal:1001 trans:1\r\n" +
                        "acct:12 bal:999 trans:1\r\n" +
                        "acct:13 bal:1001 trans:1\r\n" +
                        "acct:14 bal:999 trans:1\r\n" +
                        "acct:15 bal:1001 trans:1\r\n" +
                        "acct:16 bal:999 trans:1\r\n" +
                        "acct:17 bal:1001 trans:1\r\n" +
                        "acct:18 bal:999 trans:1\r\n" +
                        "acct:19 bal:1001 trans:1\r\n",
                outputStream.toString());
    }

    @Test
    public void transactionCoverTest() {
        var transaction = new Transaction(1, 2, 1000);
        assertEquals("from:1 to:2 amount:1000", transaction.toString());
    }
}



