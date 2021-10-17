package Sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuTests {
  public static final String hard =
      """
          3 0 0 0 0 0 0 8 0\040
          0 0 1 0 9 3 0 0 0\040
          0 4 0 7 8 0 0 0 3\040
          0 9 3 8 0 0 0 1 2\040
          0 0 0 0 4 0 0 0 0\040
          5 2 0 0 0 6 7 9 0\040
          6 0 0 0 2 1 0 4 0\040
          0 0 0 5 3 0 9 0 0\040
          0 3 0 0 0 0 0 5 1\040
          """;

  public static final String hardText =
      "3 0 0 0 0 0 0 8 0" +
      "0 0 1 0 9 3 0 0 0" +
      "0 4 0 7 8 0 0 0 3" +
      "0 9 3 8 0 0 0 1 2" +
      "0 0 0 0 4 0 0 0 0" +
      "5 2 0 0 0 6 7 9 0" +
      "6 0 0 0 2 1 0 4 0" +
      "0 0 0 5 3 0 9 0 0" +
      "0 3 0 0 0 0 0 5 1";

  public static final int[][] hardGrid = new int[][]{
      {3, 0, 0, 0, 0, 0, 0, 8, 0},
      {0, 0, 1, 0, 9, 3, 0, 0, 0},
      {0, 4, 0, 7, 8, 0, 0, 0, 3},
      {0, 9, 3, 8, 0, 0, 0, 1, 2},
      {0, 0, 0, 0, 4, 0, 0, 0, 0},
      {5, 2, 0, 0, 0, 6, 7, 9, 0},
      {6, 0, 0, 0, 2, 1, 0, 4, 0},
      {0, 0, 0, 5, 3, 0, 9, 0, 0},
      {0, 3, 0, 0, 0, 0, 0, 5, 1},
  };

  public static final String solvedHard =
      """
          3 5 7 1 6 2 4 8 9\s
          8 6 1 4 9 3 2 7 5\s
          9 4 2 7 8 5 1 6 3\s
          4 9 3 8 5 7 6 1 2\s
          7 1 6 2 4 9 5 3 8\s
          5 2 8 3 1 6 7 9 4\s
          6 8 5 9 2 1 3 4 7\s
          1 7 4 5 3 8 9 2 6\s
          2 3 9 6 7 4 8 5 1\s
          """;

  public static final int[][] solvedHardGrid = new int[][]{
      {3, 5, 7, 1, 6, 2, 4, 8, 9,},
      {8, 6, 1, 4, 9, 3, 2, 7, 5,},
      {9, 4, 2, 7, 8, 5, 1, 6, 3,},
      {4, 9, 3, 8, 5, 7, 6, 1, 2,},
      {7, 1, 6, 2, 4, 9, 5, 3, 8,},
      {5, 2, 8, 3, 1, 6, 7, 9, 4,},
      {6, 8, 5, 9, 2, 1, 3, 4, 7,},
      {1, 7, 4, 5, 3, 8, 9, 2, 6,},
      {2, 3, 9, 6, 7, 4, 8, 5, 1,},
  };

  public static final String[] solvedHardStrings = new String[]{
      "3 5 7 1 6 2 4 8 9",
      "8 6 1 4 9 3 2 7 5",
      "9 4 2 7 8 5 1 6 3",
      "4 9 3 8 5 7 6 1 2",
      "7 1 6 2 4 9 5 3 8",
      "5 2 8 3 1 6 7 9 4",
      "6 8 5 9 2 1 3 4 7",
      "1 7 4 5 3 8 9 2 6",
      "2 3 9 6 7 4 8 5 1",
  };

  public static final String solvedMedium =
      """
          5 3 4 6 7 8 9 1 2\s
          6 7 2 1 9 5 3 4 8\s
          1 9 8 3 4 2 5 6 7\s
          8 5 9 7 6 1 4 2 3\s
          4 2 6 8 5 3 7 9 1\s
          7 1 3 9 2 4 8 5 6\s
          9 6 1 5 3 7 2 8 4\s
          2 8 7 4 1 9 6 3 5\s
          3 4 5 2 8 6 1 7 9\s
          """;
  public static final String solvedEasy =
      """
          1 6 4 7 9 5 3 8 2\s
          2 8 7 4 6 3 9 1 5\s
          9 3 5 2 8 1 4 6 7\s
          3 9 1 8 7 6 5 2 4\s
          5 4 6 1 3 2 7 9 8\s
          7 2 8 9 5 4 1 3 6\s
          8 1 9 6 4 7 2 5 3\s
          6 7 3 5 2 9 8 4 1\s
          4 5 2 3 1 8 6 7 9\s
          """;

  public static final int[][] emptyGrid = new int[][]{
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
  };

  Sudoku sudoku;

  @BeforeEach
  public void setUp() {
    // default tests are for hard
    sudoku = new Sudoku(Sudoku.hardGrid);
  }

  @Test
  public void testHard() {
    sudoku.solve();
    assertEquals(sudoku.getSolutionText(), solvedHard);
  }

  @Test
  public void testMedium() {
    var sudoku = new Sudoku(Sudoku.mediumGrid);
    sudoku.solve();
    assertEquals(sudoku.getSolutionText(), solvedMedium);
  }

  @Test
  public void testEasy() {
    var sudoku = new Sudoku(Sudoku.easyGrid);
    sudoku.solve();
    assertEquals(sudoku.getSolutionText(), solvedEasy);
  }

  @Test
  public void testEmptyGrid() {
    var sudoku = new Sudoku(SudokuTests.emptyGrid);
    var solveCount = sudoku.solve();
    assertEquals(solveCount, Sudoku.MAX_SOLUTIONS);
    assertTrue(sudoku.getElapsed() >= 0);
  }

  @Test
  public void testStringsToGrid() {
    assertTrue(() -> Arrays.deepEquals(solvedHardGrid, Sudoku.stringsToGrid(solvedHardStrings)));
  }

  @Test
  public void testToString() {
    var sudoku = new Sudoku(Sudoku.hardGrid);
    assertEquals(hard, sudoku.toString());
  }

  @Test
  public void testToGrid() {
    assertTrue(() -> Arrays.deepEquals(hardGrid, Sudoku.textToGrid(hardText)));

    // 82 characters
    assertThrows(RuntimeException.class, () -> Sudoku.textToGrid(hardText + "1"));
  }

  @Test
  void testMain() {
    // https://stackoverflow.com/questions/32241057/how-to-test-a-print-method-in-java-using-junit
    PrintStream oldOut = System.out;
    ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(arrayOutputStream));
    Sudoku.main();
    System.setOut(oldOut);
    String output = arrayOutputStream.toString();
    assertTrue(output.contains("solutions"));
    assertTrue(output.contains("elapsed"));
    System.out.println(output);
  }
}
