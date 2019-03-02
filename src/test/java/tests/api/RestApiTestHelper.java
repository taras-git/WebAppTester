package tests.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static java.util.Arrays.asList;


/**
 * Created by taras on 7/30/18.
 */
public class RestApiTestHelper {

    private static final Logger LOG = LoggerFactory.getLogger(RestApiTestHelper.class);
    private final String ENVIRONMENT = Utils.getRestApiTestEnvironment();

    Object[][] getDataProviderFromList(List<?> list) {
        return list.stream()
                .map(element -> new Object[] { element } )
                .toArray(Object[][]::new);
    }

    void siteIsUp(String url) {
        get(url)
        .then()
            .contentType(ContentType.HTML)
        .and()
        .assertThat()
            .statusCode(200);
    }

    String getPostEndpoint(JsonObject jsonObject) {
        String endpoint = ENVIRONMENT + jsonObject.get("endpoint").getAsString();
        LOG.info("POST API CALL ENDPOINT: " + endpoint);
        return endpoint;
    }

    JsonObject getPostBodyAsJson(JsonObject jsonObject) {
        JsonObject body =  jsonObject.get("body").getAsJsonObject();
        LOG.info("POST API CALL BODY: " + body);
        return body;
    }

    String getGetEndpoint(String url) {
        String endpoint = ENVIRONMENT + url;
        LOG.info("GET API CALL ENDPOINT: " + endpoint);
        return endpoint;
    }

    Set<String> findDuplicates(List<String> listDuplicates) {
        final Set<String> setDuplicates = new HashSet<>();
        final Set<String> setUniqueValues = new HashSet<>();

        for (String s : listDuplicates) {
            if(!s.isEmpty()) {
                if (!setUniqueValues.add(s)) {
                    setDuplicates.add(s);
                }
            }
        }
        return setDuplicates;
    }

    Map<Boolean, List<String>> collectHttpResponses(Elements elementsWithHref) {
        Map<Boolean, List<String>> linksMap;
        linksMap = elementsWithHref
                .stream()
                .map(el -> el.attr("href"))  // get the "href" value of the element
                .map(String::trim)  // trim the text
                .filter(el -> el.contains("http"))  // get absolute links only
                .distinct()// there could be duplicate links , so find unique
                .collect(Collectors.partitioningBy(l -> HtmlUtils.getResponseCode(l) == 200)); // group the links based on the "200" response code
        return linksMap;
    }

    List<String> getLocationIdList(JSONArray locationsArray) {
        // populate locationsIdsList with actual ids
        List<String> locationsIdsList = new ArrayList<>();

        for(int i = 0; i < locationsArray.length(); i++) {
            String id = locationsArray
                    .getJSONObject(i)
                    .get("id")
                    .toString();
            locationsIdsList.add(id);
        }

        return locationsIdsList;
    }

    JsonObject getApiCallAsJson(String key, String apiCallsFile) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(JsonReader.getValue(key, apiCallsFile).toString(), JsonObject.class);
        return jsonObject;
    }

    Object[][] getApiCalls(String key, String file) {
        JsonObject apiCall = getApiCallAsJson(key, file);
        List calls = Arrays.asList(apiCall);
        return getDataProviderFromList(calls);
    }

}
