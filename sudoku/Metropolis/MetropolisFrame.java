package Metropolis;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

public class MetropolisFrame extends JFrame {
  JTextField metropolisTF = new JTextField(10);
  JTextField continentTF = new JTextField(10);
  JTextField populationTF = new JTextField(10);

  MetropolisTableModel metropolisTableModel = new MetropolisTableModel(MetropolisDb.METROPOLIS_TABLE_NAME);
  JTable metropolisTable = new JTable(metropolisTableModel);
  JScrollPane metropolisScrollPane = new JScrollPane(metropolisTable);

  JButton addButton = new JButton("Add");
  JButton searchButton = new JButton("Search");

  JComboBox<String> populationCB = new JComboBox<>(new String[]{"Population Larger Than", "Population Smaller Than"});
  JComboBox<String> matchTypeCB = new JComboBox<>(new String[]{"Exact Match", "Partial Match"});

  public MetropolisFrame() throws SQLException {
    super("Sudoku.Sudoku Solver");

    setLayout(new BorderLayout(4, 4));

    setupInputs();
    setupControls();
    add(metropolisScrollPane);

    addListeners();

    setLocationByPlatform(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(700, 600));
    pack();
    setVisible(true);
  }

  /**
   * Add listeners for button clicks
   */
  private void addListeners() {
    addButton.addActionListener(e -> metropolisTableModel.add(
        metropolisTF.getText(),
        continentTF.getText(),
        populationTF.getText()));

    searchButton.addActionListener(e -> metropolisTableModel.search(
        metropolisTF.getText(),
        continentTF.getText(),
        populationTF.getText(),
        Objects.equals(populationCB.getSelectedItem(), "Population Larger Than"),
        Objects.equals(matchTypeCB.getSelectedItem(), "Exact Match")
    ));
  }

  /**
   * Setup add & search buttons and search options
   */
  private void setupControls() {
    var controlsPanel = Box.createVerticalBox();
    var searchOptions = Box.createVerticalBox();
    searchOptions.setBorder(new TitledBorder("Search options"));

    controlsPanel.add(searchButton);
    controlsPanel.add(addButton);
    searchOptions.add(populationCB);
    searchOptions.add(matchTypeCB);
    controlsPanel.add(searchOptions);

    addButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    searchButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    populationCB.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    matchTypeCB.setAlignmentX(JComponent.LEFT_ALIGNMENT);

    populationCB.setMaximumSize(new Dimension(matchTypeCB.getMaximumSize().width, matchTypeCB.getPreferredSize().height));
    matchTypeCB.setMaximumSize(new Dimension(matchTypeCB.getMaximumSize().width, matchTypeCB.getPreferredSize().height));

    add(controlsPanel, BorderLayout.EAST);
  }

  /**
   * Setup top input fields
   */
  private void setupInputs() {
    var inputsPane = new JPanel();
    inputsPane.add(new JLabel("Metropolis:"));
    inputsPane.add(metropolisTF);
    inputsPane.add(new JLabel("Continent:"));
    inputsPane.add(continentTF);
    inputsPane.add(new JLabel("Population:"));
    inputsPane.add(populationTF);
    add(inputsPane, BorderLayout.NORTH);
  }

  public static void main(String[] args) throws Exception {
    new Metropolis.MetropolisFrame();
  }
}
