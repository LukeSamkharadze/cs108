package WebLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WebGui {
    private final WebLoader webLoader;
    public JTable urlTable;
    public JPanel mainPanel;
    public JButton singleThreadFetchButton;
    public JButton concurrentFetchButton;
    public JTextField threadCountField;
    public JLabel runningLabel;
    public JLabel completedLabel;
    public JProgressBar progressBar;
    public JButton stopButton;
    public JScrollPane urlTableScrollPane;
    public JLabel elapsedLabel;

    public WebGui(WebLoader webLoader) {
        this.webLoader = webLoader;

        initUrlTable();
        addActionListeners();
    }

    private void addActionListeners() {
        singleThreadFetchButton.addActionListener(e -> webLoader.fetchClicked(1));

        concurrentFetchButton.addActionListener(e -> {
            if (threadCountField.getText().matches("-?\\d+")) {
                webLoader.fetchClicked(Integer.parseInt(threadCountField.getText()));
            }
        });

        stopButton.addActionListener(e -> webLoader.stopClicked());
    }

    private void initUrlTable() {
        var urlTableModel = new DefaultTableModel(
                new String[][]{},
                new String[]{"URL", "STATUS"}
        );

        urlTable.setModel(urlTableModel);
    }
}
