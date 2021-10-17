import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharGridTest {

    @Test
    public void testCharArea1() {
        char[][] grid = new char[][]{
                {'a', 'y', ' '},
                {'x', 'a', 'z'},
        };


        CharGrid cg = new CharGrid(grid);

        assertEquals(4, cg.charArea('a'));
        assertEquals(1, cg.charArea('z'));
    }

    @Test
    public void testCharArea0() {
        char[][] grid = new char[][]{
                {'a', 'y', ' '},
                {'x', 'a', 'z'},
        };


        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.charArea('l'));
    }

    @Test
    public void testCharArea2() {
        char[][] grid = new char[][]{
                {'c', 'a', ' '},
                {'b', ' ', 'b'},
                {' ', ' ', 'a'}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(6, cg.charArea('a'));
        assertEquals(3, cg.charArea('b'));
        assertEquals(1, cg.charArea('c'));
    }

    @Test
    public void testCountPlus1() {
        char[][] grid = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(1, cg.countPlus(' '));
    }

    @Test
    public void testCountPlus2() {
        char[][] grid = new char[][]{
                {' ', ' ', ' '},
                {' ', 'a', ' '},
                {' ', ' ', ' '}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.countPlus(' '));
        assertEquals(0, cg.countPlus('a'));
    }

    @Test
    public void testCountPlus3() {
        char[][] grid = new char[][]{
                {' ', 'a', ' '},
                {' ', 'a', ' '},
                {'a', 'a', 'a'},
                {' ', 'a', ' '},
                {' ', ' ', ' '}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.countPlus(' '));
        assertEquals(0, cg.countPlus('b'));
        assertEquals(0, cg.countPlus('a'));
    }
}
