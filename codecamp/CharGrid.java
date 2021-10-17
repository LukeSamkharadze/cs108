public class CharGrid {
    private char[][] grid;

    public CharGrid(char[][] grid) {
        this.grid = grid;
    }

    private int countChars(char ch, int y0, int x0, int y1, int x1) {
        var count = 0;

        for (var y = y0; y <= y1; y++)
            for (var x = x0; x <= x1; x++)
                if (grid[y][x] == ch)
                    count++;

        return count;
    }

    public int charArea(char ch) {
        var contains = countChars(ch, 0, 0, grid.length - 1, grid[0].length - 1);
        if(contains == 0)
            return 0;

        var min = (grid.length) * (grid[0].length);

        for (var y = 0; y < grid.length; y++)
            for (var x = 0; x < grid[0].length; x++)
                for (var yy = y; yy < grid.length; yy++)
                    for (var xx = x; xx < grid[0].length; xx++)
                        if (contains == countChars(ch, y, x, yy, xx))
                            if ((yy - y + 1) * (xx - x + 1) < min)
                                min = (yy - y + 1) * (xx - x + 1);

        return min;
    }

    private int directionSize(int y0, int x0, int dy, int dx) {
        var size = 0;
        for (int y = y0, x = x0; y >= 0 && x >= 0 && y < grid.length && x < grid[0].length && grid[y][x] == grid[y0][x0]; y += dy, x += dx, size++) {
        }
        return size;
    }

    // TODO
    public int countPlus(char c) {
        var count = 0;

        for (var y = 0; y < grid.length; y++)
            for (var x = 0; x < grid[0].length; x++)
                if (grid[y][x] == c &&
                        directionSize(y, x, -1, 0) >= 2 &&
                        directionSize(y, x, -1, 0) == directionSize(y, x, 1, 0) &&
                        directionSize(y, x, -1, 0) == directionSize(y, x, 0, 1) &&
                        directionSize(y, x, -1, 0) == directionSize(y, x, 0, -1))
                    count++;

        return count;
    }
}
