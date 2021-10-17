import java.util.Arrays;
import java.util.stream.IntStream;

public class Board {
  private final boolean DEBUG = true;

  private final int width;
  private final int height;

  private boolean[][] grid;
  private int[] heights;
  private int[] widths;
  private int maxHeight;

  private boolean[][] oldGrid;
  private int[] oldHeights;
  private int[] oldWidths;
  private int oldMaxHeight;

  boolean committed;

  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    this.grid = new boolean[width][height];
    this.oldGrid = new boolean[width][height];
    this.committed = true;
    this.heights = new int[width];
    this.oldHeights = new int[width];
    this.widths = new int[height];
    this.oldWidths = new int[height];
  }

  public void sanityCheck() {
    if (DEBUG) {
      for (var width : this.widths)
        assert (width != this.getWidth());

      assert (this.getMaxHeight() == Arrays.stream(this.heights).max().getAsInt());
    }
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public int getMaxHeight() {
    return this.maxHeight;
  }

  public int dropHeight(Piece piece, int x) {
    return IntStream.range(0, piece.getSkirt().length)
        .map(index -> piece.getSkirt()[index] + this.heights[x + index])
        .max().getAsInt();
  }

  public int getColumnHeight(int x) {
    return this.heights[x];
  }

  public int getRowWidth(int y) {
    return this.widths[y];
  }

  public boolean getGrid(int x, int y) {
    if (x >= 0 && x < this.width && y >= 0 && y < this.height)
      return grid[x][y];
    return true;
  }

  public static final int PLACE_OK = 0;
  public static final int PLACE_ROW_FILLED = 1;
  public static final int PLACE_OUT_BOUNDS = 2;
  public static final int PLACE_BAD = 3;

  public void makeBackup() {
    this.oldHeights = Arrays.copyOf(this.heights, this.heights.length);
    this.oldWidths = Arrays.copyOf(this.widths, this.widths.length);
    this.oldMaxHeight = this.maxHeight;
    for (int i = 0; i < this.grid.length; i++) this.oldGrid[i] = Arrays.copyOf(this.grid[i], this.grid[i].length);
  }

  public int place(Piece piece, int x, int y) {
    if (!committed)
      throw new RuntimeException("place commit problem");

    makeBackup();

    int result = PLACE_OK;

    for (var tPoint : piece.getBody())
      if (!this.getGrid(x + tPoint.x, y + tPoint.y)) {
        if (++this.widths[y + tPoint.y] == getWidth())
          result = result == PLACE_OK ? PLACE_ROW_FILLED : result;
        this.heights[x + tPoint.x] = Math.max(this.heights[x + tPoint.x], y + tPoint.y + 1);
        this.grid[x + tPoint.x][y + tPoint.y] = true;
      } else result = PLACE_BAD;

    this.maxHeight = Arrays.stream(this.heights).max().getAsInt();

    if (x < 0 || x + piece.getWidth() > this.getWidth() ||
        y < 0 || y + piece.getHeight() > this.getHeight())
      result = PLACE_OUT_BOUNDS;

    this.committed = false;

    return result;
  }

  public int clearRows() {
    int rowsCleared = 0;

//    makeBackup(); brahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh

    for (int y = 0; y < this.getHeight(); y++)
      if (this.widths[y] == this.getWidth()) {
        rowsCleared++;

        for (int yy = y; yy < this.getHeight() - 1; yy++) {
          for (int x = 0; x < this.getWidth(); x++)
            this.grid[x][yy] = this.grid[x][yy + 1];

          this.widths[yy] = this.widths[yy + 1];
        }


        for (int x = 0; x < this.getWidth(); x++) {
          this.grid[x][this.getHeight() - 1] = false;
          this.heights[x]--;
        }
        this.widths[this.getHeight() - 1] = 0;

        y--; // try again to clear same row
      }

    this.maxHeight = Arrays.stream(this.heights).max().getAsInt();

    this.committed = false;

    sanityCheck();

    return rowsCleared;
  }


  public void undo() {
    if (!this.committed) { // brahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
      committed = true;
      this.widths = this.oldWidths;
      this.heights = this.oldHeights;
      this.maxHeight = this.oldMaxHeight;
      for (int i = 0; i < this.grid.length; i++) this.grid[i] = Arrays.copyOf(this.oldGrid[i], this.oldGrid[i].length);
    }
  }

  public void commit() {
    committed = true;
  }

  public String toString() {
    StringBuilder buff = new StringBuilder();
    for (int y = height - 1; y >= 0; y--) {
      buff.append('|');
      for (int x = 0; x < width; x++)
        if (getGrid(x, y)) buff.append('+');
        else buff.append(' ');
      buff.append("|\n");
    }
    buff.append("-".repeat(Math.max(0, width + 2)));
    return (buff.toString());
  }
}
