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

    public static int getResponseCode(String link) throws IOException {
        URL url;
        HttpURLConnection con = null;
        Integer responseCode = 0;

        url = new URL(link);
        con = (HttpURLConnection) url.openConnection();
        responseCode = con.getResponseCode();

        if (null != con)
            con.disconnect();

        return responseCode;
    }

}
