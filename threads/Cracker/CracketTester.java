package Cracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;

import static Cracker.Cracker.*;
import static java.lang.System.setOut;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CracketTester {
    OutputStream outputStream;

    @BeforeEach()
    public void reset() {
        outputStream = new ByteArrayOutputStream();
        var printS = new PrintStream(outputStream);
        setOut(printS);

        results.clear();
    }

    @Test
    public void testCracker1() throws NoSuchAlgorithmException, InterruptedException {
        main(new String[]{});

        assertEquals("""
                        No arguments given, started basic threading.\r
                        Found next passwords: \r
                        xyz\r
                        ALL DONE.\r
                        """,
                outputStream.toString());
    }


    @Test
    public void testCracker2() throws NoSuchAlgorithmException, InterruptedException {
        main(new String[]{"xyz"});

        assertEquals("66b27417d37e024c46526c2f6d358a754fc552f3\r\n", outputStream.toString());
    }

    @Test
    public void testCracker3() throws NoSuchAlgorithmException, InterruptedException {
        main(new String[]{"34800e15707fae815d7c90d49de44aca97e2d759", "2", "2"});

        assertEquals("""
                        Found next passwords: \r
                        a!\r
                        ALL DONE.\r
                        """,
                outputStream.toString());
    }


    @Test
    public void testCracker4() throws NoSuchAlgorithmException, InterruptedException {
        main(new String[]{"34800e15707fae815d7c90d49de44aca97e2d759", "2"});

        assertEquals("""
                        Found next passwords: \r
                        a!\r
                        ALL DONE.\r
                        """,
                outputStream.toString());
    }

    @Test
    public void testCracker5() throws NoSuchAlgorithmException, InterruptedException {
        main(new String[]{"34800e15707fae815d7c90d49de44aca97e2d759", "2", "2", "bla"});

        assertEquals("too many arguments!\r\n", outputStream.toString());
    }

    @Test
    public void testCracker6() {
        assertArrayEquals(hexToArray("24a24f"), hexToArray("24a24f"));
        assertArrayEquals(hexToArray("24a24f"), hexToArray("24a24f"));
        assertArrayEquals(hexToArray("24a24f"), hexToArray("24a24f"));
        assertArrayEquals(hexToArray("24a24f"), hexToArray("24a24f"));
        assertArrayEquals(hexToArray("24a24f"), hexToArray("24a24f"));
    }
}







