package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlUtils {

    public static Elements getElements(String startUrl) throws IOException {
        Document doc = Jsoup.connect(startUrl).get();
        return doc.select("a[href]");
    }

    public static int getResponseCode(String link) {
        URL url;
        HttpURLConnection con = null;
        int responseCode = 0;

        try {
            url = new URL(link);
            con = (HttpURLConnection) url.openConnection();
            responseCode = con.getResponseCode();
        } catch (Exception e) {
            // skip
        } finally {
            if (null != con)
                con.disconnect();
        }
        return responseCode;
    }

}
