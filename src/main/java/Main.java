import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by anon on 15/10/2015.
 */
public class Main {
    public static void main(String[] args)
            throws MalformedURLException, IOException {
        for(int i = 19088; i != 19087; i--) {
            BufferedInputStream in = null;
            FileOutputStream fout = null;
            try {
                System.setProperty("http.agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

                in = new BufferedInputStream(new URL("http://www.hltv.org/interfaces/download.php?demoid=" + i).
                        openStream());
                fout = new FileOutputStream("D:/demos/demo" + i + ".zip");

                final byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }
        }
    }
}
