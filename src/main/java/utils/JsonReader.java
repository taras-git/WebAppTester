package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * Created by taras on 7/17/18.
 */
public class JsonReader {

    static String propertyFile = "artifacts/properties/general_property.json";

    public static String getPropertyFileValue(String key) {
        JSONParser jsonParser = new JSONParser();
        String value = null;

        try {
            Object obj = jsonParser.parse(new FileReader(propertyFile));
            JSONObject jsonObject = (JSONObject) obj;
            value = (String) jsonObject.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
}