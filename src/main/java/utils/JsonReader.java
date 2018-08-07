package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * Created by taras on 7/17/18.
 */
public class JsonReader {

    static String propertyFile = "artifacts/properties/general_property.json";
    static String urlsFile = "artifacts/properties/urls.json";
    static String usersFile = "artifacts/properties/users.json";

    public static Boolean getBoolean(String key) {
        return (Boolean) getValue(key, propertyFile);
    }
    public static String getString(String key) {
        return (String) getValue(key, propertyFile);
    }
    public static String getUrl(String key) {
        return (String) getValue(key, urlsFile);
    }
    public static String getUser(String key) {
        return (String) getValue(key, usersFile);
    }
    public static String getLocation(String key) {
        return (String) getValue(key, usersFile);
    }

    private static <T extends Object> T getValue(String key, String fileName) {
        JSONParser jsonParser = new JSONParser();
        Object value = null;

        try {
            Object obj = jsonParser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            value = jsonObject.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) value;
    }

}