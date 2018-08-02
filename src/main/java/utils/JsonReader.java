package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * Created by taras on 7/17/18.
 */
public class JsonReader {

    static String propertyFile = "artifacts/properties/general_property.json";

    public static Boolean getBoolean(String key) {
        return (Boolean) getValue(key);
    }

    public static String getString(String key) {
        return (String) getValue(key);
    }

    private static <T extends Object> T getValue(String key) {
        JSONParser jsonParser = new JSONParser();
        Object value = null;

        try {
            Object obj = jsonParser.parse(new FileReader(propertyFile));
            JSONObject jsonObject = (JSONObject) obj;
            value = jsonObject.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) value;
    }
}