package WebLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.*;
import static java.text.MessageFormat.format;

public class WebWorker extends Thread {
    private final String urlString;
    private final WebLoader webLoader;
    private final int rowIndex;

    private String result;

    public WebWorker(WebLoader webLoader, String urlString, int rowIndex) {
        this.webLoader = webLoader;
        this.urlString = urlString;
        this.rowIndex = rowIndex;
    }

    public void run() {
        download();
        webLoader.workerFinished(result, rowIndex);
    }

    void download() {
        InputStream input = null;
        try {
            var connection = new URL(urlString).openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();

            input = connection.getInputStream();
            var reader = new BufferedReader(new InputStreamReader(input));

            var array = new char[1000];
            var startTime = currentTimeMillis();

            var size = 0;
            int len;
            while ((len = reader.read(array, 0, array.length)) > 0) {
                size += len;
                sleep(100);
            }
            long endTime = currentTimeMillis();

            result = format("{0} {1}ms {2}bytes", new SimpleDateFormat("HH:mm:ss").format(new Date(startTime)), endTime - startTime, size);
        } catch (IOException ignored) {
            result = "err";
        } catch (InterruptedException exception) {
            result = "interrupted";
        } finally {
            if (isInterrupted())
                result = "interrupted";
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ignored) {
            }
        }
    }
}
