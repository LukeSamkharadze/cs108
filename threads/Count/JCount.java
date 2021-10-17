package Count;

import javax.swing.*;
import java.awt.*;

import static java.lang.Integer.parseInt;
import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.SwingUtilities.invokeLater;

public class JCount extends JPanel {

    static public void main(String[] args) {
        invokeLater(() -> {
            var frame = new JFrame("The Count");
            frame.setLayout(new BoxLayout(frame.getContentPane(), Y_AXIS));
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.add(new JCount());
            frame.add(new JCount());
            frame.add(new JCount());
            frame.add(new JCount());
            frame.pack();
            frame.setVisible(true);
        });
    }

    private final JButton clickStart;
    private final JButton clickStop;
    private final JTextField textField;
    private final JLabel label;
    private Worker thread = null;
    private static final int SLEEP_TIME = 100;

    private void addListeners() {
        clickStop.addActionListener(e -> {
            if (thread != null) {
                thread.interrupt();
                thread = null;
            }
        });

        clickStart.addActionListener(e -> {
            if (thread != null)
                thread.interrupt();
            thread = new Worker(textField.getText());
            thread.start();
        });
    }

    public JCount() {
        super();

        setLayout(new BoxLayout(this, Y_AXIS));
        textField = new JTextField("100000000", 10);
        label = new JLabel("0");
        clickStart = new JButton("Start");
        clickStop = new JButton("Stop");
        addListeners();

        add(textField);
        add(label);
        add(clickStart);
        add(clickStop);
        add(Box.createRigidArea(new Dimension(0, 40)));
    }

    protected class Worker extends Thread {
        private final int data;

        public Worker(String data) {
            this.data = parseInt(data);
        }

        @Override
        public void run() {
            for (var i = 0; i <= data; i++) {
                if (!isInterrupted()) {
                    final var s = i + "";
                    if (i % 10000 == 0) {
                        try {
                            sleep(SLEEP_TIME);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    invokeLater(() -> label.setText(s));
                } else break;
            }
        }
    }
}

