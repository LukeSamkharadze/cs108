// JTetris.java

import java.awt.*;
import javax.swing.*;

import java.util.*;

import java.awt.Toolkit;


public class JTetris extends JComponent {
  public static final int WIDTH = 10;
  public static final int HEIGHT = 20;

  public static final int TOP_SPACE = 4;

  protected boolean testMode = false;
  public final int TEST_LIMIT = 100;

  // Is drawing optimized
  // (default false, so debugging is easier)
  protected boolean DRAW_OPTIMIZE = false;

  protected Board board;
  protected Piece[] pieces;

  protected Piece currentPiece;
  protected int currentX;
  protected int currentY;
  protected boolean moved;  // did the player move the piece

  protected Piece newPiece;
  protected int newX;
  protected int newY;

  protected boolean gameOn;  // true if we are playing
  protected int count;     // how many pieces played so far
  protected long startTime;  // used to measure elapsed time
  protected Random random;   // the random generator for new pieces

  protected JLabel countLabel;
  protected JLabel scoreLabel;
  protected int score;
  protected JLabel timeLabel;
  protected JButton startButton;
  protected JButton stopButton;
  protected javax.swing.Timer timer;
  protected JSlider speed;
  protected JCheckBox testButton;

  public final int DELAY = 400;  // milliseconds per tick

  JTetris(int pixels) {
    super();

    // Set component size to allow given pixels for each block plus
    // a 1 pixel border around the whole thing.
    setPreferredSize(new Dimension((WIDTH * pixels) + 2, (HEIGHT + TOP_SPACE) * pixels + 2));
    gameOn = false;

    pieces = Piece.getPieces();
    board = new Board(WIDTH, HEIGHT + TOP_SPACE);

    registerKeyboardAction(e -> tick(LEFT), "left", KeyStroke.getKeyStroke('a'), WHEN_IN_FOCUSED_WINDOW);
    registerKeyboardAction(e -> tick(RIGHT), "right", KeyStroke.getKeyStroke('d'), WHEN_IN_FOCUSED_WINDOW);
    registerKeyboardAction(e -> tick(ROTATE), "rotate", KeyStroke.getKeyStroke('w'), WHEN_IN_FOCUSED_WINDOW);
    registerKeyboardAction(e -> tick(DROP), "drop", KeyStroke.getKeyStroke('s'), WHEN_IN_FOCUSED_WINDOW);

    timer = new javax.swing.Timer(DELAY, e -> tick(DOWN));

    requestFocusInWindow();
  }

  public void startGame() {
    board = new Board(WIDTH, HEIGHT + TOP_SPACE);

    repaint();

    count = 0;
    score = 0;
    updateCounters();
    gameOn = true;

    testMode = testButton.isSelected();

    if (testMode) random = new Random(0);  // same seq every time
    else random = new Random(); // diff seq each game

    enableButtons();
    timeLabel.setText(" ");
    addNewPiece();
    timer.start();
    startTime = System.currentTimeMillis();
  }

  private void enableButtons() {
    startButton.setEnabled(!gameOn);
    stopButton.setEnabled(gameOn);
  }

  public void stopGame() {
    gameOn = false;
    enableButtons();
    timer.stop();

    long delta = (System.currentTimeMillis() - startTime) / 10;
    timeLabel.setText(delta / 100.0 + " seconds");
  }

  public int setCurrent(Piece piece, int x, int y) {
    int result = board.place(piece, x, y);

    if (result <= Board.PLACE_ROW_FILLED) {
      // repaint the rect where it used to be
      if (currentPiece != null) repaintPiece(currentPiece, currentX, currentY);
      currentPiece = piece;
      currentX = x;
      currentY = y;
      // repaint the rect where it is now
      repaintPiece(currentPiece, currentX, currentY);
    } else {
      board.undo();
    }

    return (result);
  }

  public Piece pickNextPiece() {
    return pieces[(int) (pieces.length * random.nextDouble())];
  }

  public void addNewPiece() {
    count++;
    score++;

    if (testMode && count == TEST_LIMIT + 1) {
      stopGame();
      return;
    }

    board.commit();
    currentPiece = null;

    Piece piece = pickNextPiece();

    int px = (board.getWidth() - piece.getWidth()) / 2;
    int py = board.getHeight() - piece.getHeight();

    int result = setCurrent(piece, px, py);

    if (result > Board.PLACE_ROW_FILLED) {
      stopGame();
    }

    updateCounters();
  }

  private void updateCounters() {
    countLabel.setText("Pieces " + count);
    scoreLabel.setText("Score " + score);
  }

  /**
   * Figures a new position for the current piece
   * based on the given verb (LEFT, RIGHT, ...).
   * The board should be in the committed state --
   * i.e. the piece should not be in the board at the moment.
   * This is necessary so dropHeight() may be called without
   * the piece "hitting itself" on the way down.
   * <p>
   * Sets the ivars newX, newY, and newPiece to hold
   * what it thinks the new piece position should be.
   * (Storing an intermediate result like that in
   * ivars is a little tacky.)
   */
  public void computeNewPosition(int verb) {
    // As a starting point, the new position is the same as the old
    newPiece = currentPiece;
    newX = currentX;
    newY = currentY;

    // Make changes based on the verb
    switch (verb) {
      case LEFT -> newX--;
      case RIGHT -> newX++;
      case ROTATE -> {
        newPiece = newPiece.fastRotation();

        // tricky: make the piece appear to rotate about its center
        // can't just leave it at the same lower-left origin as the
        // previous piece.
        newX = newX + (currentPiece.getWidth() - newPiece.getWidth()) / 2;
        newY = newY + (currentPiece.getHeight() - newPiece.getHeight()) / 2;
      }
      case DOWN -> newY--;
      case DROP -> {
        newY = board.dropHeight(newPiece, newX);

        // trick: avoid the case where the drop would cause
        // the piece to appear to move up
        if (newY > currentY) {
          newY = currentY;
        }
      }
      default -> throw new RuntimeException("Bad verb");
    }

  }


  public static final int ROTATE = 0;
  public static final int LEFT = 1;
  public static final int RIGHT = 2;
  public static final int DROP = 3;
  public static final int DOWN = 4;

  public void tick(int verb) {
    if (!gameOn) return;

    if (currentPiece != null) {
      board.undo();  // remove the piece from its old position
    }

    // Sets the newXXX ivars
    computeNewPosition(verb);

    // try out the new position (rolls back if it doesn't work)
    int result = setCurrent(newPiece, newX, newY);

    // if row clearing is going to happen, draw the
    // whole board so the green row shows up
    if (result == Board.PLACE_ROW_FILLED) {
      repaint();
    }

    boolean failed = (result >= Board.PLACE_OUT_BOUNDS);

    // if it didn't work, put it back the way it was
    if (failed) {
      if (currentPiece != null) board.place(currentPiece, currentX, currentY);
      repaintPiece(currentPiece, currentX, currentY);
    }
		
    if (failed && verb == DOWN && !moved) {  // it's landed

      int cleared = board.clearRows();
      if (cleared > 0) {
        // score goes up by 5, 10, 20, 40 for row clearing
        // clearing 4 gets you a beep!
        switch (cleared) {
          case 1 -> score += 5;
          case 2 -> score += 10;
          case 3 -> score += 20;
          case 4 -> {
            score += 40;
            Toolkit.getDefaultToolkit().beep();
          }
          default -> score += 50;  // could happen with non-standard pieces
        }
        updateCounters();
        repaint();  // repaint to show the result of the row clearing
      }

      // if the board is too tall, we've lost
      if (board.getMaxHeight() > board.getHeight() - TOP_SPACE) {
        stopGame();
      }
      // Otherwise add a new piece and keep playing
      else {
        addNewPiece();
      }
    }

    // Note if the player made a successful non-DOWN move --
    // used to detect if the piece has landed on the next tick()
    moved = (!failed && verb != DOWN);
  }

  public void repaintPiece(Piece piece, int x, int y) {
    if (DRAW_OPTIMIZE) {
      int px = xPixel(x);
      int py = yPixel(y + piece.getHeight() - 1);
      int pwidth = xPixel(x + piece.getWidth()) - px;
      int pheight = yPixel(y - 1) - py;

      repaint(px, py, pwidth, pheight);
    } else {
      repaint();
    }
  }

  private float dX() {
    return (((float) (getWidth() - 2)) / board.getWidth());
  }

  private float dY() {
    return (((float) (getHeight() - 2)) / board.getHeight());
  }

  private int xPixel(int x) {
    return (Math.round(1 + (x * dX())));
  }

  private int yPixel(int y) {
    return (Math.round(getHeight() - 1 - (y + 1) * dY()));
  }

  public void paintComponent(Graphics g) {
    // Draw a rect around the whole thing
    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

    // Draw the line separating the top
    int spacerY = yPixel(board.getHeight() - TOP_SPACE - 1);
    g.drawLine(0, spacerY, getWidth() - 1, spacerY);

    // check if we are drawing with clipping
    // Shape shape = g.getClip();
    Rectangle clip = null;
    if (DRAW_OPTIMIZE) {
      clip = g.getClipBounds();
    }

    // Factor a few things out to help the optimizer
    final int dx = Math.round(dX() - 2);
    final int dy = Math.round(dY() - 2);
    final int bWidth = board.getWidth();

    int x, y;
    // Loop through and draw all the blocks
    // left-right, bottom-top
    for (x = 0; x < bWidth; x++) {
      int left = xPixel(x);  // the left pixel

      // right pixel (useful for clip optimization)
      int right = xPixel(x + 1) - 1;

      // skip this x if it is outside the clip rect
      if (DRAW_OPTIMIZE && clip != null) {
        if ((right < clip.x) || (left >= (clip.x + clip.width))) continue;
      }

      // draw from 0 up to the col height
      final int yHeight = board.getColumnHeight(x);
      for (y = 0; y < yHeight; y++) {
        if (board.getGrid(x, y)) {
          boolean filled = (board.getRowWidth(y) == bWidth);
          if (filled) g.setColor(Color.green);

          g.fillRect(left + 1, yPixel(y) + 1, dx, dy);  // +1 to leave a white border

          if (filled) g.setColor(Color.black);
        }
      }
    }
  }

  public void updateTimer() {
    double value = ((double) speed.getValue()) / speed.getMaximum();
    timer.setDelay((int) (DELAY - value * DELAY));
  }

  public JComponent createControlPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // COUNT
    countLabel = new JLabel("0");
    panel.add(countLabel);

    // SCORE
    scoreLabel = new JLabel("0");
    panel.add(scoreLabel);

    // TIME
    timeLabel = new JLabel(" ");
    panel.add(timeLabel);

    panel.add(Box.createVerticalStrut(12));

    // START button
    startButton = new JButton("Start");
    panel.add(startButton);
    startButton.addActionListener(e -> startGame());

    // STOP button
    stopButton = new JButton("Stop");
    panel.add(stopButton);
    stopButton.addActionListener(e -> stopGame());

    enableButtons();

    JPanel row = new JPanel();

    // SPEED slider
    panel.add(Box.createVerticalStrut(12));
    row.add(new JLabel("Speed:"));
    speed = new JSlider(0, 200, 75);  // min, max, current
    speed.setPreferredSize(new Dimension(100, 15));

    updateTimer();
    row.add(speed);

    panel.add(row);
    // when the slider changes, sync the timer to its value
    speed.addChangeListener(e -> updateTimer());

    testButton = new JCheckBox("Test sequence");
    panel.add(testButton);

    return panel;
  }

  public static JFrame createFrame(JTetris tetris) {
    JFrame frame = new JFrame("Stanford Tetris");
    JComponent container = (JComponent) frame.getContentPane();
    container.setLayout(new BorderLayout());

    // Install the passed in JTetris in the center.
    container.add(tetris, BorderLayout.CENTER);

    // Create and install the panel of controls.
    JComponent controls = tetris.createControlPanel();
    container.add(controls, BorderLayout.EAST);

    // Add the quit button last so it's at the bottom
    controls.add(Box.createVerticalStrut(12));
    JButton quit = new JButton("Quit");
    controls.add(quit);
    quit.addActionListener(e -> System.exit(0));

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();

    return frame;
  }

  public static void main(String[] args) {
    JTetris tetris = new JTetris(16);
    JFrame frame = JTetris.createFrame(tetris);
    frame.setVisible(true);
  }
}
