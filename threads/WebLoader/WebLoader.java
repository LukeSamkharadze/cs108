package WebLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import static java.text.MessageFormat.format;
import static java.util.stream.IntStream.range;
import static javax.swing.SwingUtilities.*;

public class WebLoader {
    private final AtomicInteger workersCompleted = new AtomicInteger(0);
    private final WebGui webGui = new WebGui(this);
    private Semaphore maxThreadsRunningSemaphore;

    private long startTime;
    private int maxThreads;
    private Thread mainWorkerThread;

    public static void main(String[] args) throws IOException {
        var webLoader = new WebLoader();

        webLoader.initFrame();
        webLoader.initUrls("WebLoader/links.txt");
    }

    private void initUrls(String file) throws IOException {
        var bufferedReader = new BufferedReader(new FileReader(file));

        String currentLine;
        for (var rowIndex = 0; (currentLine = bufferedReader.readLine()) != null; rowIndex++) {
            ((DefaultTableModel) webGui.urlTable.getModel()).setRowCount(webGui.urlTable.getModel().getRowCount() + 1);
            webGui.urlTable.getModel().setValueAt(currentLine, rowIndex, 0);
        }
    }

    private void initFrame() {
        var frame = new JFrame("WebLoader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(webGui.mainPanel);
        frame.setVisible(true);
        frame.pack();
    }

    public void fetchClicked(int maxThreads) {
        webGui.singleThreadFetchButton.setEnabled(false);
        webGui.concurrentFetchButton.setEnabled(false);

        startTime = System.currentTimeMillis();
        workersCompleted.set(0);

        webGui.progressBar.setMaximum(webGui.urlTable.getRowCount());
        webGui.progressBar.setValue(0);
        webGui.runningLabel.setText("Running: 1");
        webGui.stopButton.setEnabled(true);

        for (var rowId = 0; rowId < webGui.urlTable.getRowCount(); rowId++) webGui.urlTable.setValueAt("", rowId, 1);

        this.maxThreads = maxThreads;

        fetchUrls(maxThreads);
    }

    private void fetchUrls(int maxThreads) {
        mainWorkerThread = new Thread(() -> {
            var workers = getWorkers();

            maxThreadsRunningSemaphore = new Semaphore(maxThreads);

            for (var urlId = 0; urlId < webGui.urlTable.getRowCount(); urlId++) {
                try {
                    maxThreadsRunningSemaphore.acquire();
                    workers.get(urlId).start();
                    invokeLater(() -> webGui.runningLabel.setText(format("Running: {0}", 1 + maxThreads - maxThreadsRunningSemaphore.availablePermits())));
                } catch (InterruptedException e) {
                    workers.forEach(Thread::interrupt);
                    break;
                }
            }

            for (var webWorker : workers) {
                try {
                    webWorker.join();
                } catch (InterruptedException e) {
                    workers.forEach(Thread::interrupt);
                    try {
                        webWorker.join();
                    } catch (InterruptedException ee) {
                        ee.printStackTrace();
                    }
                }
            }

            final var elapsedTime = (System.currentTimeMillis() - startTime) / (double) 1000;

            invokeLater(() -> {
                webGui.singleThreadFetchButton.setEnabled(true);
                webGui.concurrentFetchButton.setEnabled(true);
                webGui.elapsedLabel.setText(format("Elapsed: {0}", elapsedTime));
                webGui.stopButton.setEnabled(false);
                webGui.runningLabel.setText("Running: 0");
            });
        });

        mainWorkerThread.start();
    }

    private List<WebWorker> getWorkers() {
        return range(0, webGui.urlTable.getRowCount())
                .mapToObj(o ->
                        new WebWorker(
                                this,
                                webGui.urlTable.getValueAt(o, 0).toString(),
                                o
                        ))
                .toList();
    }

    public void workerFinished(String result, int rowIndex) {
        maxThreadsRunningSemaphore.release();

        invokeLater(() -> {
            webGui.urlTable.getModel().setValueAt(result, rowIndex, 1);
            webGui.progressBar.setValue(workersCompleted.incrementAndGet());
            webGui.completedLabel.setText(format("Completed: {0}", workersCompleted.get()));
            webGui.runningLabel.setText(format("Running: {0}", 1 + maxThreads - maxThreadsRunningSemaphore.availablePermits()));
        });
    }

    public void stopClicked() {
        if (mainWorkerThread != null) {
            webGui.stopButton.setEnabled(false);
            mainWorkerThread.interrupt();
        }
    }
}
