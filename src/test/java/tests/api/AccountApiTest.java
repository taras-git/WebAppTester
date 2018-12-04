package tests.api;

import com.google.gson.JsonObject;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AccountApiTest extends AbstractApiTest {

    private static final Logger LOG = LoggerFactory.getLogger(AccountApiTest.class);
    private final RestApiTestHelper testHelper = new RestApiTestHelper();
    private final String accountApiFile = "artifacts/properties/account_api_calls.json";

    @DataProvider
    public Object[][] getAccountUserLogin() {
        List apiGetCalls = Arrays.asList(testHelper.getApiCallAsJson("account_user_login", accountApiFile));
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getAccountUserLogin")
    public void verifyAccountUserLoginTest(JsonObject jsonObject) {
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        JsonObject jsonBody = testHelper.getPostBodyAsJson(jsonObject);

        //get user id and session
        Response response = getJsonSuccessResponse(endpoint, jsonBody);

        String userId = getUserId(response);
        String stormSession = getStormSession(response);
        endpoint = endpoint.replace("login/", "");
        LOG.info("\nID: {} \nSESSION: {} \nENDPOINT: {}", userId, stormSession, endpoint);

        Header header1 = new Header(STORM_SESSION, stormSession);
        Header header2 = new Header(USER_ID, userId);
        Headers headers = new Headers(header1, header2);

        response = getJsonSuccessResponse(endpoint, headers);

        String userLogin = jsonBody.get("login").getAsString();
        String userEmail = getEmail(response);
        String userIdReturned = getId(response);

        assertEquals(userLogin, userEmail);
        assertEquals(userId, userIdReturned);
    }

}
