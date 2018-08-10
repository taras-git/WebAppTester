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
    static String locationsFile = "artifacts/properties/locations.json";
    static String emailsFile = "artifacts/properties/emails.json";

    public static Boolean getBoolean(String key) {
        return (Boolean) getValue(key, propertyFile);
    }

    public static String getString(String key) {
        return (String) getValue(key, propertyFile);
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

    public static String getConfirmationSubject() {
        return (String) getValue("confirmation_subject", emailsFile);
    }


    public static String getLocation(String key) {
        return (String) getValue(key, locationsFile);
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