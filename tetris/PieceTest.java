import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
  private Piece pyr1, pyr2, pyr3, pyr4;
  private Piece s, sRotated;

  @BeforeEach
  public void init() throws Exception {
    pyr1 = new Piece(Piece.PYRAMID_STR);
    pyr2 = pyr1.computeNextRotation();
    pyr3 = pyr2.computeNextRotation();
    pyr4 = pyr3.computeNextRotation();
    s = new Piece(Piece.S1_STR);
    sRotated = s.computeNextRotation();
  }

  @Test
  public void testSampleSize() {
    assertEquals(3, pyr1.getWidth());
    assertEquals(2, pyr1.getHeight());
    assertEquals(2, pyr2.getWidth());
    assertEquals(3, pyr2.getHeight());
    Piece l = new Piece(Piece.STICK_STR);
    assertEquals(1, l.getWidth());
    assertEquals(4, l.getHeight());
  }


  @Test
  public void testSampleSkirt() {
    assertTrue(Arrays.equals(new int[]{0, 0, 0}, pyr1.getSkirt()));
    assertTrue(Arrays.equals(new int[]{1, 0, 1}, pyr3.getSkirt()));
    assertTrue(Arrays.equals(new int[]{0, 0, 1}, s.getSkirt()));
    assertTrue(Arrays.equals(new int[]{1, 0}, sRotated.getSkirt()));
  }

  @Test
  public void testEquals() {
    assertTrue(pyr1.equals(new Piece(Piece.PYRAMID_STR)));
    assertFalse(pyr1.equals(new Piece(Piece.L1_STR)));
    assertFalse(pyr1.equals(new Piece("0 0")));
  }

  @Test
  public void testFastRotation() {
    assertTrue(pyr1.computeNextRotation().equals(Piece.getPieces()[6].fastRotation()));
  }

  @Test
  public void testParse() {
    assertThrows(RuntimeException.class, () -> new Piece("zd"));
  }
}
