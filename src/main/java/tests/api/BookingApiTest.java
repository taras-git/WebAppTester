package tests.api;

import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Booking;
import utils.JsonReader;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


/**
 * Created by taras on 7/30/18.
 */
public class BookingApiTest {

    private static final Logger LOG = LoggerFactory.getLogger(BookingApiTest.class);

    private final RestApiTestHelper testHelper = new RestApiTestHelper();
    private final BookingApiTestHelper bath = new BookingApiTestHelper();

    private final String bookingApiFile = "artifacts/properties/booking_api_calls.json";


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

    @Test(dataProvider = "getPostApiWithoutLogin")
    public void verifyApiPostWithoutLoginTest(JsonObject jsonObject) {
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        jsonObject.remove("endpoint");

        Map<String, Object> jsonBody = testHelper.getPostBody(jsonObject);

        bath.getJsonResponse(endpoint, jsonBody);

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

    @Test(dataProvider = "getPostApiWithLogin")
    public void verifyApiPostLoginTest(JsonObject jsonObject) {
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        jsonObject.remove("endpoint");

        Map<String, Object> jsonBody = testHelper.getPostBody(jsonObject);

        //get user id and session
        Response response = bath.getJsonResponse(endpoint, jsonBody);

        String userId = response.path("userId");
        String stormSession = response.path("stormSession");
        endpoint = endpoint.replace("login/", "");
        LOG.info("\nID: {} \nSESSION: {} \nENDPOINT: {}", userId, stormSession, endpoint);

        //validate user
        response = given()
                .when()
                .header("STORMSESSION", stormSession)
                .header("userId", userId)
                .get(endpoint)
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .extract()
                .response();

        String userLogin = jsonObject.get("login").getAsString();
        String userEmail = response.path("email");
        String userIdReturned = response.path("id");

        assertEquals(userLogin, userEmail);
        assertEquals(userId, userIdReturned);
    }

    @DataProvider
    public Object[][] getBookingGetCalls() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_get_calls", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingGetCalls")
    public void verifyBookingGetCallsTest(String url) {
        String endpoint = testHelper.getGetEndpoint(url);
        bath.getJsonResponse(endpoint);

    }

    @DataProvider
    public Object[][] getBookingIdCalls() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_id_get_calls", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingIdCalls")
    public void verifyBookingIdGetCallsTest(String url) {
        String endpoint = testHelper.getGetEndpoint("/storm/booking/");

        Response response = bath.getJsonResponse(endpoint);

        Booking[] bookings = bath.getBookingsFromResponse(response);
        String firstBookingId = bookings[0].getBookingId();

        endpoint = testHelper.getGetEndpoint(url + firstBookingId);
        response = bath.getJsonResponse(endpoint);
        bookings = bath.getBookingsFromResponse(response);
        String bookingIdReturned = bookings[0].getBookingId();

        assertEquals(firstBookingId, bookingIdReturned);
    }

    @DataProvider
    public Object[][] getBookingIdMystiqueCalls() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_id_mystique_get_calls", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingIdMystiqueCalls")
    public void verifyBookingIdMystiqueGetCallsTest(String url) {
        String endpoint = testHelper.getGetEndpoint("/storm/booking/");
        Response response = bath.getJsonResponse(endpoint);

        Booking[] bookings = bath.getBookingsFromResponse(response);
        String firstBookingId = bookings[0].getBookingId();

        endpoint = testHelper.getGetEndpoint(url + firstBookingId);
        response = bath.getJsonResponse(endpoint);
        String bookingIdReturned = response.path("bookingId");

        assertEquals(firstBookingId, bookingIdReturned);
    }

    @DataProvider
    public Object[][] getBookingPaginated() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_get_paginated", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingPaginated")
    public void verifyBookingPaginated(String url) {
        String endpoint = testHelper.getGetEndpoint(url);

        endpoint = endpoint.replace("{page}", "1");
        endpoint = endpoint.replace("{itemsPerPage}", "100");
        Response response = bath.getJsonResponse(endpoint);
        Booking[] bookings = bath.getBookingsFromResponse(response);

        assertEquals(bookings.length, 100);

        endpoint = endpoint.replace("100", "50");
        response = bath.getJsonResponse(endpoint);
        bookings = bath.getBookingsFromResponse(response);

        assertEquals(bookings.length, 50);
    }

    @DataProvider
    public Object[][] getBookingXavier() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_xavier_get_call", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingXavier")
    public void verifyBookingXavier(String url) {
        String startEndpoint = testHelper.getGetEndpoint("/storm/booking/paginated/1/1");
        String endpoint = testHelper.getGetEndpoint(url);
        Response response = bath.getJsonResponse(startEndpoint);

        Booking[] bookings = bath.getBookingsFromResponse(response);
        String bookingId = bookings[0].getBookingId();

        endpoint = endpoint.replace("{bookingId}", bookingId);
        response = bath.getJsonResponse(endpoint);
        bookings = bath.getBookingsFromResponse(response);
        String xavierBookingId = bookings[0].getBookingId();

        assertEquals(bookingId, xavierBookingId);
    }

}
