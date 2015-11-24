import com.google.common.base.Stopwatch;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by anon on 15/10/2015.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        long totalTime = 0;
        System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.1");
        int archiveDownloadAttempts = 0;

        for(int i = 10; i >= 9; i--, archiveDownloadAttempts++) {
            BufferedInputStream in = null;
            FileOutputStream fout = null;
            try {
                URL url = new URL("http://www.hltv.org/interfaces/download.php?demoid=" + i);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.disconnect();
                String demoName = null;

                try {
                    demoName = "id-" + i + "-" + conn.getHeaderField("content-disposition").substring(21);
                } catch (NullPointerException | IndexOutOfBoundsException e){
                    demoName = "id-" + i + "-";
                }

                if(demoName == null){
                    demoName = "id-" + i + "-";
                }

                in = new BufferedInputStream(url.openStream());
                fout = new FileOutputStream("D:/demos/" + demoName);

                final byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }

                if(archiveDownloadAttempts % 10 == 0 && archiveDownloadAttempts != 0) {
                    int downloadRangeTop = i + 9;

                    System.out.println("Downloaded 10 more archives (" + i + " - " + downloadRangeTop + ")");
                    System.out.println("Total downloaded this session: " + archiveDownloadAttempts);
                    System.out.println("Time taken to download last 10 archives: " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds");

                    totalTime += stopwatch.elapsed(TimeUnit.SECONDS);
                    stopwatch.reset();
                    stopwatch.start();
                }
            } catch (MalformedURLException e){
                System.out.println("MalformedURLException on demoid: " + i);
            } catch (IOException e){
                System.out.println("IOException on demoid: " + i);
            } catch (Exception e) {
                System.out.println(e.getClass().toGenericString() + " on demoid: " + i);
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }
        }

        System.out.println("Program done.  Downloaded " + archiveDownloadAttempts + " archives in " +
                totalTime + " seconds.");
    }
}
