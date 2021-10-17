import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
  Board b;
  Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

  @BeforeEach
  protected void setUp() {
    b = new Board(3, 6);

    pyr1 = new Piece(Piece.PYRAMID_STR);
    pyr2 = pyr1.computeNextRotation();
    pyr3 = pyr2.computeNextRotation();
    pyr4 = pyr3.computeNextRotation();

    s = new Piece(Piece.S1_STR);
    sRotated = s.computeNextRotation();

    b.place(pyr1, 0, 0);
  }

  @Test
  public void testSample0() {
    assertEquals(2, b.dropHeight(pyr1, 0));
  }

  @Test
  public void testSample1() {
    assertEquals(1, b.getColumnHeight(0));
    assertEquals(2, b.getColumnHeight(1));
    assertEquals(2, b.getMaxHeight());
    assertEquals(3, b.getRowWidth(0));
    assertEquals(1, b.getRowWidth(1));
    assertEquals(0, b.getRowWidth(2));
  }

  @Test
  public void testSample2() {
    b.commit();
    int result = b.place(sRotated, 1, 1);
    assertEquals(Board.PLACE_OK, result);
    assertEquals(1, b.getColumnHeight(0));
    assertEquals(4, b.getColumnHeight(1));
    assertEquals(3, b.getColumnHeight(2));
    assertEquals(4, b.getMaxHeight());
  }

  @Test
  public void testSample3() {
    b.commit();
    int result = b.place(pyr1, 0, 0);
    assertEquals(Board.PLACE_BAD, result);
  }

  @Test
  public void testSample4() {
    assertThrows(RuntimeException.class, () -> b.place(pyr1, 0, 0));
  }

  @Test
  public void testSample5() {
    b.commit();
    int result = b.place(pyr1, 100, 100);
    assertEquals(Board.PLACE_OUT_BOUNDS, result);
  }

  @Test
  public void testSample6() {
    b.commit();
    int result = b.place(pyr1, b.getWidth() - 1, b.getHeight() - 1);
    assertEquals(Board.PLACE_OUT_BOUNDS, result);
    b.undo();
    assertFalse(b.getGrid(b.getWidth() - 1, b.getHeight() - 1));
  }

  @Test
  public void testSample7() {
    b.commit();
    int rowsCleared = b.clearRows();
    assertEquals(1, rowsCleared);
  }

  @Test
  public void testSample9() {
    String a = """
        |   |
        |   |
        |   |
        |   |
        | + |
        |+++|
        -----""";
    assertEquals(a, b.toString());
  }
}
