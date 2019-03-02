package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by taras on 7/17/18.
 */
public class JsonReader {

    private static String propertyFile = "artifacts/properties/general_property.json";
    private static Gson gson = new Gson();

    public static Boolean getBoolean(String key) {
        return getBoolean(key, propertyFile);
    }

    public static String getString(String key) {
        return getString(key, propertyFile);
    }

    public static String getString(String key, String fileName) {
        return (String) getValue(key, fileName);
    }


    public static <T extends Object> T getValue(String key, String fileName) {
        org.json.simple.JSONObject jsonObject = getJsonObject(fileName);
        Object value = jsonObject.get(key);

        return (T) value;
    }

    private static Boolean getBoolean(String key, String fileName) {
        return (Boolean) getValue(key, fileName);
    }

    private static ArrayList<String> getArray(String key, String fileName) {
        return gson.fromJson(getValue(key, fileName).toString(), ArrayList.class);
    }


    public static ArrayList<String> getJsonStringArrayList(String key, String jsonFile) {
        JsonArray jsonArray = gson.fromJson(getValue(key, jsonFile).toString(), JsonArray.class);
        ArrayList<String> arrayList = new ArrayList<>();

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++){
                arrayList.add(jsonArray.get(i).getAsString());
            }
        }
        return arrayList;
    }


    private static org.json.simple.JSONObject getJsonObject(String fileName) {
        JSONParser jsonParser = new JSONParser();
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