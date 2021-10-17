package Sudoku;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Sudoku {
  public static final int[][] easyGrid = Sudoku.stringsToGrid(
      "1 6 4 0 0 0 0 0 2",
      "2 0 0 4 0 3 9 1 0",
      "0 0 5 0 8 0 4 0 7",
      "0 9 0 0 0 6 5 0 0",
      "5 0 0 1 0 2 0 0 8",
      "0 0 8 9 0 0 0 3 0",
      "8 0 9 0 4 0 2 0 0",
      "0 7 3 5 0 9 0 0 1",
      "4 0 0 0 0 0 6 7 9");
  public static final int[][] mediumGrid = Sudoku.stringsToGrid(
      "530070000",
      "600195000",
      "098000060",
      "800060003",
      "400803001",
      "700020006",
      "060000280",
      "000419005",
      "000080079");
  // 1 solution this way, 6 solutions if the 7 is changed to 0
  public static final int[][] hardGrid = Sudoku.stringsToGrid(
      "3 0 0 0 0 0 0 8 0",
      "0 0 1 0 9 3 0 0 0",
      "0 4 0 7 8 0 0 0 3",
      "0 9 3 8 0 0 0 1 2",
      "0 0 0 0 4 0 0 0 0",
      "5 2 0 0 0 6 7 9 0",
      "6 0 0 0 2 1 0 4 0",
      "0 0 0 5 3 0 9 0 0",
      "0 3 0 0 0 0 0 5 1");
  public static final int SIZE = 9;
  public static final int PART = 3;
  public static final int MAX_SOLUTIONS = 100;

  //===============================================

  public static void main(String... args) {
    Sudoku sudoku;
    sudoku = new Sudoku(hardGrid);

    System.out.println(sudoku);
    int count = sudoku.solve();
    System.out.println("solutions:" + count);
    System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
  }

  public static String getSolveInfo(Sudoku sudoku) {
    return sudoku.getSolutionText() + "\nsolutions:" + sudoku.solveCount +
           "\nelapsed:" + sudoku.getElapsed() + "ms";
  }

  public static class Point {
    public int y;
    public int x;

    Point(int y, int x) {
      this.y = y;
      this.x = x;
    }
  }

  private final int[][] grid;
  private int[][] solvedGrid;

  private int solveCount;

  private long startingTime = -1;
  private long endingTime = -1;

  private final ArrayList<Point> points = new ArrayList<>();

  private void assignCells() {
    for (int y = 0; y < grid.length; y++)
      for (int x = 0; x < grid.length; x++)
        if (grid[y][x] == 0)
          points.add(new Point(y, x));

    points.sort(Comparator.comparingInt(o -> getVariants(o.y, o.x).size()));
  }

  private Set<Integer> getVariants(int y, int x) {
    Set<Integer> variants = new HashSet<>();

    for (int i = 1; i < 10; i++) variants.add(i);

    for (int i = 0; i < 9; i++) {
      variants.remove(grid[y][i]);
      variants.remove(grid[i][x]);
      variants.remove(grid[y - y % PART + i / PART][x - x % PART + i % PART]);
    }

    return variants;
  }

  public Sudoku(int[][] grid) {
    this.grid = grid;
    assignCells();
  }

  public Sudoku(String text) {
    this.grid = textToGrid(text);
    assignCells();
  }


  public void solveHelper(int curCellId) {
    if (curCellId == points.size()) {
      solvedGrid = getGridCopy(grid);
      solveCount++;
      return;
    }

    Point currPoint = points.get(curCellId);
    for (var variant : getVariants(currPoint.y, currPoint.x)) {
      grid[currPoint.y][currPoint.x] = variant;
      solveHelper(curCellId + 1);
      grid[currPoint.y][currPoint.x] = 0;

      if (solveCount == MAX_SOLUTIONS)
        return;
    }
  }


  public int solve() {
    startingTime = System.currentTimeMillis();
    solveHelper(0);
    endingTime = System.currentTimeMillis();
    return solveCount;
  }

  public String getSolutionText() {
    return toString(solvedGrid);
  }

  public long getElapsed() {
    return endingTime - startingTime;
  }

  public static int[][] stringsToGrid(String... rows) {
    int[][] result = new int[rows.length][];
    for (int row = 0; row < rows.length; row++)
      result[row] = stringToInts(rows[row]);
    return result;
  }

  public static int[][] textToGrid(String text) {
    int[] nums = stringToInts(text);
    if (nums.length != SIZE * SIZE)
      throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);

    int[][] result = new int[SIZE][SIZE];
    int count = 0;
    for (int row = 0; row < SIZE; row++)
      for (int col = 0; col < SIZE; col++) {
        result[row][col] = nums[count];
        count++;
      }

    for (int i = 0; i < 10; i++) {
      int finalI = i;
      new Thread(() -> System.out.println(finalI));
    }
    return result;
  }

  public static int[] stringToInts(String string) {
    int[] a = new int[string.length()];
    int found = 0;
    for (int i = 0; i < string.length(); i++)
      if (Character.isDigit(string.charAt(i))) {
        a[found] = Integer.parseInt(string.substring(i, i + 1));
        found++;
      }
    int[] result = new int[found];
    System.arraycopy(a, 0, result, 0, found);
    return result;
  }

  @Override
  public String toString() {
    return toString(grid);
  }

  public String toString(int[][] grid) {
    var stringBuilder = new StringBuilder();
    for (var columns : grid) {
      for (var cell : columns)
        stringBuilder.append(String.format("%s ", cell));
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }

  private int[][] getGridCopy(int[][] grid) {
    int[][] newGrid = new int[grid.length][];

    for (int i = 0; i < grid.length; i++) {
      newGrid[i] = new int[grid.length];
      System.arraycopy(grid[i], 0, newGrid[i], 0, grid.length);
    }

    return newGrid;
  }
}
