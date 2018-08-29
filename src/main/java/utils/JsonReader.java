package utils;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by taras on 7/17/18.
 */
public class JsonReader {

    private static String propertyFile = "artifacts/properties/general_property.json";
    private static String urlsFile = "artifacts/properties/urls.json";
    private static String usersFile = "artifacts/properties/users.json";
    private static String locationsFile = "artifacts/properties/locations.json";
    private static String emailsFile = "artifacts/properties/emails.json";
    private static String timePatternFile = "artifacts/properties/time_pattern.json";

    public static Boolean getBoolean(String key) {
        return (Boolean) getValue(key, propertyFile);
    }

    public static String getString(String key) {
        return (String) getValue(key, propertyFile);
    }

    public static String getBookTimePattern() {
        return (String) getValue("book_car_date_time", timePatternFile);
    }

    public static String getTimePattern(String key) {
        return (String) getValue(key, timePatternFile);
    }

    public static String getUrl(String key) {
        return (String) getValue(key, urlsFile);
    }

    public static String getUserEmail(String key) {
        return Utils.getUserEmail((String) getValue(key, usersFile));
    }

    public static String getUserPassword(String key) {
        return Utils.getUserPassword((String) getValue(key, usersFile));
    }

    public static String getUserImap(String key) {
        return (String) getValue(key, usersFile);
    }

    public static String getConfirmationSubjectEN() {
        return (String) getValue("confirmation_subject_en", emailsFile);
    }

    public static String getConfirmationSubjectDE() {
        return (String) getValue("confirmation_subject_de", emailsFile);
    }

    public static String getLocation(String key) {
        return (String) getValue(key, locationsFile);
    }

    private static <T extends Object> T getValue(String key, String fileName) {
        org.json.simple.JSONObject jsonObject = getJsonObject(fileName);
        Object value = jsonObject.get(key);

        return (T) value;
    }

    private static org.json.simple.JSONObject getJsonObject(String fileName) {
        JSONParser jsonParser = new JSONParser();
        Object value = null;
        org.json.simple.JSONObject jsonObject = null;

        try {
            Object obj = jsonParser.parse(new FileReader(fileName));
            jsonObject = (org.json.simple.JSONObject) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static String readAll(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            builder.append((char) cp);
        }
        return builder.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException {
        InputStream inputStream = new URL(url).openStream();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(reader);
            return new JSONObject(jsonText);
        } finally {
            inputStream.close();
        }
    }

}