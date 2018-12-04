package tests.api;

import com.google.gson.JsonObject;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.HtmlUtils;
import utils.JsonReader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static utils.JsonReader.readJsonFromUrl;


/**
 * Created by taras on 7/30/18.
 */
public class RestApiTest extends AbstractApiTest{

    private static final Logger LOG = LoggerFactory.getLogger(RestApiTest.class);
    private final RestApiTestHelper testHelper = new RestApiTestHelper();

    @DataProvider
    public Object[][] urls(){
        return testHelper.getDataProviderFromList(testHelper.pageNames);
    }

    @Test(dataProvider = "urls")
    public void verifySiteIsUpTest(String url) {
        LOG.info(">>> Start new test for: <<<" + url + ">>>");
        try {
            testHelper.siteIsUp(url);
            LOG.info("<<<" + url + ">>> is up!");
        } catch (Exception e) {
            LOG.error("Can't opening site: " + url + " :: " + e.getMessage());
            throw new RuntimeException("ERROR opening site: " + url + " :: " + e.getStackTrace());
        }
    }

    @DataProvider
    public Object[][] getStartUrls(){
        return testHelper.getDataProviderFromList(testHelper.startUrls);
    }

    @Test(dataProvider =  "getStartUrls")
    public void verifyLinksReturnSuccessCodeTest(String url){
        Map<Boolean, List<String>> linksMap = null;
        LOG.info("Testing page: " + url);

        try{
            Elements elementsWithHref = HtmlUtils.getElements(url);
            linksMap = testHelper.collectHttpResponses(elementsWithHref);
        } catch (Exception e) {
            LOG.info("Parsing exception: ", e);
        }

        SoftAssert softAssertion= new SoftAssert();
        if(!linksMap.get(false).isEmpty()){
            linksMap.get(false).forEach((v) -> {
                LOG.error("FOUND URL WITH CODE != 200 : " + v);
                softAssertion.fail("Stauts code !=200 for: " + v);
            });
        }
        softAssertion.assertAll();
    }

    @DataProvider
    public Object[][] getPostApiWithoutLogin() {
        ArrayList<JsonObject> apiPostCalls = JsonReader.getJsonObjectArrayList("api_post_calls");
        apiPostCalls.forEach(c -> LOG.info("apiPostCalls : " + c.toString()));

        apiPostCalls = (ArrayList<JsonObject>) apiPostCalls
                .stream()
                .filter(call -> !call //api call shalt not contain "login"
                        .get("endpoint")
                        .getAsString()
                        .contains("login"))
                .collect(Collectors.toList());

        apiPostCalls.forEach(c -> LOG.info("filtered apiPostCalls : " + c.toString()));
        return testHelper.getDataProviderFromList(apiPostCalls);
    }

    @Test(dataProvider= "getPostApiWithoutLogin")
    public void verifyApiPostWithoutLoginTest(JsonObject jsonObject){
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        removeEndpointFromJsonObject(jsonObject);
        Map<String, Object> jsonBody = getPostBodyAsMap(jsonObject);

        getJsonSuccessResponse(endpoint, jsonBody);
    }

    @DataProvider
    public Object[][] getPostApiWithLogin() {
        ArrayList<JsonObject> apiPostCalls = JsonReader.getJsonObjectArrayList("api_post_calls");
        apiPostCalls.forEach(c -> LOG.info("apiPostCalls : " + c.toString()));

        apiPostCalls = (ArrayList<JsonObject>) apiPostCalls
                .stream()
                .filter(call -> call //api call should contain "login"
                        .get("endpoint")
                        .toString()
                        .contains("login"))
                .collect(Collectors.toList());

        apiPostCalls.forEach(c -> LOG.info("filtered apiPostCalls : " + c.toString()));
        return testHelper.getDataProviderFromList(apiPostCalls);
    }

    @Test(dataProvider= "getPostApiWithLogin")
    public void verifyApiPostLoginTest(JsonObject jsonObject){
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        removeEndpointFromJsonObject(jsonObject);
        Map<String, Object> jsonBody = getPostBodyAsMap(jsonObject);

        //get user id and session
        Response response = getJsonSuccessResponse(endpoint, jsonBody);

        String userId = getUserId(response);
        String stormSession = getStormSession(response);
        endpoint = endpoint.replace("login/", "");
        LOG.info("\nID: {} \nSESSION: {} \nENDPOINT: {}", userId, stormSession, endpoint);

        Header header1 = new Header(STORM_SESSION, stormSession);
        Header header2 = new Header(USER_ID, userId);
        Headers headers = new Headers(header1, header2);

        //validate user
        response = getJsonSuccessResponse(endpoint, headers);

        String userLogin = jsonObject.get("login").getAsString();
        String userEmail = getEmail(response);
        String userIdReturned = getId(response);

        assertEquals(userLogin,userEmail);
        assertEquals(userId, userIdReturned);
    }


    @DataProvider
    public Object[][] getGetParams() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("api_get_calls");
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider= "getGetParams")
    public void verifyApiGetCallsTest(String url){
        //this method verifies API GET calls with failing tests
        String endpoint = testHelper.getGetEndpoint(url);

        getJsonSuccessResponse(endpoint);
    }

    @Test
    public void verifyStationsIdsUniqueAndNotEmptyTest() throws IOException {
        String url = JsonReader.getApiCallsUrl("api_call_url") + "/storm/station/";
        JSONObject jsonObject = readJsonFromUrl(url);
        JSONArray locationsArray = jsonObject.getJSONArray("locations");

        List<String> locationsIdsList = testHelper.getLocationIdList(locationsArray);

        // check for duplicated ids
        if(testHelper.findDuplicates(locationsIdsList).size() > 0){
            LOG.error("FOUND DUPLICATED IDs : " + testHelper.findDuplicates(locationsIdsList));
            Assert.fail("FOUND DUPLICATED IDs");
        }

        // check for empty ids
        SoftAssert softAssertion= new SoftAssert();
        for(String id : locationsIdsList){
            if(id.isEmpty()){
                softAssertion.fail("FOND EMPTY LOCATION ID!!!");
            }
        }
        softAssertion.assertAll();
    }

}
