package tests.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Booking;
import utils.JsonReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

public abstract class AbstractApiTest {

    final String bookingApiFile = "artifacts/properties/booking_api_calls.json";
    final String accountApiFile = "artifacts/properties/account_api_calls.json";

    final String url = JsonReader.getValue(
            "api_call_url",
            "artifacts/properties/api_calls.json").toString();
    final String loginUrl = url + "/storm/account/user/login/";
    final String bookingUrl = url + "/storm/booking/";

    public final static String STORM_SESSION = "STORMSESSION";
    public final static String BOOKING_ID = "bookingId";
    public final static String USER_ID = "userId";
    public final static String ID = "id";

    private static final Logger LOG = LoggerFactory.getLogger(AbstractApiTest.class);

    final RestApiTestHelper testHelper = new RestApiTestHelper();

    void removeEndpointFromJsonObject(JsonObject jsonObject) {
        jsonObject.remove("endpoint");
    }

    String getStormSession(Response response) {
        return response.path("stormSession");
    }

    String getUserId(Response response) {
        return response.path("userId");
    }

    String getEmail(Response response) {
        return response.path("email");
    }

    String getBookingId(Response response) {
        return response.path("bookingId");
    }

    String getId(Response response) {
        return response.path("id");
    }

    Booking[] getBookingsFromResponse(Response response) {
        return response
                .jsonPath()
                .getObject("bookings", Booking[].class);
    }

    String getBookingIdRsoProcessed(String url) {
        String bookingId = null;
        Response response;
        response = getResponse(url);
        Booking[] bookings = getBookingsFromResponse(response);

        for (Booking b : bookings){
            if (b.getRsoProcessed()) {
                bookingId = b.getBookingId();
                break;
            }
        }
        return bookingId;
    }

    Map<String, Object> getPostBodyAsMap(JsonObject jsonObject) {
        // create a POST body
        Map<String, Object> jsonBody = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();

        for(Map.Entry<String,JsonElement> entry : entrySet){
            jsonBody.put(entry.getKey(), jsonObject.get(entry.getKey()));
            LOG.info("API CALL POST BODY: " +entry.getKey()+ " : " + jsonObject.get(entry.getKey()));
        }
        return jsonBody;
    }

    Response getResponse(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }

    Response getResponse(String endpoint, JsonObject jsonBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }


    Response getResponse(String endpoint, JsonObject jsonBody, Header header) {
        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .header(header)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    Response getJsonSuccessResponse(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }

    Response getJsonSuccessResponse(String endpoint, Map<String, Object> jsonBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    Response getJsonSuccessResponse(String endpoint, JsonObject jsonBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    Response getJsonSuccessResponse(String endpoint, Headers headers) {
        return given()
                .when()
                .headers(headers)
                .get(endpoint)
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }

    Response getJsonSuccessResponse(String endpoint, Header header) {
        return given()
                .when()
                .header(header)
                .get(endpoint)
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }

    Response getJsonSuccessResponse(String endpoint, JsonObject jsonBody, Header header) {
        return given()
                .when()
                .body(jsonBody)
                .header(header)
                .get(endpoint)
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }

}
