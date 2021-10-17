import javax.swing.*;

import java.awt.*;

public class JBrainTetris extends JTetris {
  private Brain brain;

  private boolean samePiece;

  JBrainTetris(int pixels) {
    super(pixels);
    this.brain = new DefaultBrain();
  }

  public static void main(String[] args) {
    var BrainTetris = new JBrainTetris(16);
    var frame = JTetris.createFrame(BrainTetris);
    frame.setVisible(true);
  }

  private JSlider adversaryLevelJS;
  private JLabel adversaryStatusJL;

  private JCheckBox animateModeJC;
  private JCheckBox brainModeJC;

  public JComponent createControlPanel() {
    var panel = (JPanel) super.createControlPanel();

    this.brainModeJC = new JCheckBox("Brain active");
    this.animateModeJC = new JCheckBox("Animate fall");
    this.animateModeJC.setSelected(true); // default bro 🙂

    panel.add(new JLabel("Brain:"));
    panel.add(this.brainModeJC);
    panel.add(this.animateModeJC);

    var little = new JPanel();

    this.adversaryLevelJS = new JSlider(0, 100, 0);
    this.adversaryLevelJS.setPreferredSize(new Dimension(100, 15));
    this.adversaryStatusJL = new JLabel("ok");

    little.add(new JLabel("Adversary:"));
    little.add(this.adversaryLevelJS);
    panel.add(little);

    var labelPanel = new JPanel();
    labelPanel.add(this.adversaryStatusJL);
    panel.add(labelPanel);


    return panel;
  }

  Brain.Move move;

  public void tick(int verb) {
    if (this.brainModeJC.isSelected() && verb == DOWN) {
      if (!this.samePiece) {
        this.samePiece = true;
        this.board.undo();
        move = this.brain.bestMove(board, currentPiece, board.getHeight(), null);
      }

      if (move != null && !move.piece.equals(currentPiece))
        super.tick(ROTATE);
      if (move != null && move.x > this.currentX)
        super.tick(RIGHT);
      else if (move != null && move.x < this.currentX)
        super.tick(LEFT);
      else if (move != null && !this.animateModeJC.isSelected() && move.x == currentX && currentY > move.y)
        super.tick(DROP);
    }

    super.tick(verb);
  }

  public void addNewPiece() {
    this.samePiece = false;
    super.addNewPiece();
  }

  public Piece pickNextPiece() {
    if (random.nextInt(99) >= this.adversaryLevelJS.getValue()) {
      this.adversaryStatusJL.setText("ok");
      return super.pickNextPiece();
    } else {
      this.adversaryStatusJL.setText("*ok*");
      double minScore = 0;

      var peace_out = super.pickNextPiece(); // peace
      for (var piece : this.pieces) {

        board.undo();
        var nextMove = brain.bestMove(board, piece, board.getHeight(), null);

        if (nextMove != null && nextMove.score > minScore) {
          peace_out = piece;
          minScore = nextMove.score;
        }
      }

      return peace_out;
    }

  }

}
