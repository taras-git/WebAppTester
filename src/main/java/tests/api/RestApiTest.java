package tests.api;

import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.JsonReader.readJsonFromUrl;


/**
 * Created by taras on 7/30/18.
 */
public class RestApiTest {

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
    public Object[][] getPostParams() {
        ArrayList<JsonObject> apiPostCalls = JsonReader.getJsonObjectArrayList("api_post_calls");
        return testHelper.getDataProviderFromList(apiPostCalls);
    }

    @Test(dataProvider= "getPostParams")
    public void verifyApiPostCallsTest(JsonObject jsonObject){
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        jsonObject.remove("endpoint");

        Map<String, Object> jsonBody = testHelper.getPostBody(jsonObject);

        given()
            .contentType(ContentType.JSON)
            .body(jsonBody)
        .when()
            .post(endpoint)
        .then()
            .statusCode(200)
        .and()
            .contentType(ContentType.JSON);
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

        given()
        .when()
            .get(endpoint)
        .then()
            .statusCode(200)
        .and()
            .contentType(ContentType.JSON);
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
