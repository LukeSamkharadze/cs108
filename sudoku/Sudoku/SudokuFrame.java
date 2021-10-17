package Sudoku;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SudokuFrame extends JFrame {
  JTextArea inputTextArea = new JTextArea(15, 20);
  JTextArea outputTextArea = new JTextArea(15, 20);
  JButton checkButton = new JButton("Check");
  JCheckBox autoCheckCheckBox = new JCheckBox("Auto Check");

  public SudokuFrame() {
    super("Sudoku.Sudoku Solver");

    setLayout(new BorderLayout(4, 4));

    setupTextAreas();
    setupControls();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  private void setupControls() {
    var controlsPanel = Box.createHorizontalBox();
    controlsPanel.add(checkButton);
    controlsPanel.add(autoCheckCheckBox);
    add(controlsPanel, BorderLayout.SOUTH);

    checkButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        drawOutput();
      }
    });
  }

  private void drawOutput() {
    try {
      var sudoku = new Sudoku(inputTextArea.getText());
      sudoku.solve();
      outputTextArea.setText(Sudoku.getSolveInfo(sudoku));
    } catch (Exception exception) {
      outputTextArea.setText("INVALID");
    }
  }

  private void setupTextAreas() {
    inputTextArea.setBorder(new TitledBorder("Puzzle"));
    outputTextArea.setBorder(new TitledBorder("Solution"));

    add(inputTextArea, BorderLayout.WEST);
    add(outputTextArea, BorderLayout.EAST);

    inputTextArea.getDocument().addDocumentListener(new DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
        if (autoCheckCheckBox.isSelected())
          drawOutput();
      }

      public void removeUpdate(DocumentEvent e) {
        if (autoCheckCheckBox.isSelected())
          drawOutput();
      }

      public void changedUpdate(DocumentEvent e) {
        if (autoCheckCheckBox.isSelected())
          drawOutput();
      }
    });
  }

  public static void main(String[] args) {
    new SudokuFrame();
  }
}
