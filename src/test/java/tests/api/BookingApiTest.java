package tests.api;

import com.google.gson.JsonObject;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.Booking;
import utils.JsonReader;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Created by taras on 7/30/18.
 */
public class BookingApiTest extends AbstractApiTest{

    private static final Logger LOG = LoggerFactory.getLogger(BookingApiTest.class);

    @DataProvider
    public Object[][] getBookingGetCalls() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_get_calls", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingGetCalls")
    public void verifyBookingGetCallsTest(String url) {
        String endpoint = testHelper.getGetEndpoint(url);
        getJsonSuccessResponse(endpoint);
    }

    @DataProvider
    public Object[][] getBookingIdCalls() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_id", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingIdCalls")
    public void verifyBookingIdGetCallsTest(String url) {
        String endpoint = testHelper.getGetEndpoint("/storm/booking/");

        Response response = getJsonSuccessResponse(endpoint);

        Booking[] bookings = getBookingsFromResponse(response);
        String firstBookingId = bookings[0].getBookingId();

        endpoint = testHelper.getGetEndpoint(url + firstBookingId);
        response = getJsonSuccessResponse(endpoint);
        bookings = getBookingsFromResponse(response);
        String bookingIdReturned = bookings[0].getBookingId();

        assertEquals(firstBookingId, bookingIdReturned);
    }

    @DataProvider
    public Object[][] getBookingMystiqueId() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_mystique_id", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingMystiqueId")
    public void verifyBookingMystiqueIdTest(String url) {
        String endpoint = testHelper.getGetEndpoint("/storm/booking/");
        Response response = getJsonSuccessResponse(endpoint);

        Booking[] bookings = getBookingsFromResponse(response);
        String firstBookingId = bookings[0].getBookingId();

        endpoint = testHelper.getGetEndpoint(url + firstBookingId);
        response = getJsonSuccessResponse(endpoint);
        String bookingIdReturned = getBookingId(response);

        assertEquals(firstBookingId, bookingIdReturned);
    }

    @DataProvider
    public Object[][] getBookingPaginated() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_paginated_page_items", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingPaginated")
    public void verifyBookingPaginatedTest(String url) {
        String endpoint = testHelper.getGetEndpoint(url);

        endpoint = endpoint.replace("{page}", "1");
        endpoint = endpoint.replace("{itemsPerPage}", "100");
        Response response = getJsonSuccessResponse(endpoint);
        Booking[] bookings = getBookingsFromResponse(response);

        assertEquals(bookings.length, 100);

        endpoint = endpoint.replace("100", "50");
        response = getJsonSuccessResponse(endpoint);
        bookings = getBookingsFromResponse(response);

        assertEquals(bookings.length, 50);
    }

    @DataProvider
    public Object[][] getBookingXavier() {
        ArrayList<String> apiGetCalls = JsonReader.getJsonStringArrayList("booking_xavier", bookingApiFile);
        return testHelper.getDataProviderFromList(apiGetCalls);
    }

    @Test(dataProvider = "getBookingXavier")
    public void verifyBookingXavierTest(String url) {
        String startEndpoint = testHelper.getGetEndpoint("/storm/booking/paginated/1/1");
        String endpoint = testHelper.getGetEndpoint(url);
        Response response = getJsonSuccessResponse(startEndpoint);

        Booking[] bookings = getBookingsFromResponse(response);
        String bookingId = bookings[0].getBookingId();

        endpoint = endpoint.replace("{bookingId}", bookingId);
        response = getJsonSuccessResponse(endpoint);
        bookings = getBookingsFromResponse(response);
        String xavierBookingId = bookings[0].getBookingId();

        assertEquals(bookingId, xavierBookingId);
    }

    @DataProvider
    public Object[][] getBookingByEmail() {
        return testHelper.getApiCalls("booking_by_email_get_call", bookingApiFile);
    }

    @Test(dataProvider = "getBookingByEmail")
    public void verifyGetBookingByEmailTest(JsonObject jsonObject) {
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        removeEndpointFromJsonObject(jsonObject);

        Response response = getJsonSuccessResponse(loginUrl, jsonObject);

        String stormSession = getStormSession(response);
        String email = jsonObject.get("login").getAsString();

        endpoint = endpoint.replace("{email}", email);

        getJsonSuccessResponse(endpoint, new Header(STORM_SESSION, stormSession));
    }

    @DataProvider
    public Object[][] getBookingCerebraProcessed() {
        return testHelper.getApiCalls("booking_cerebra_processed_call", bookingApiFile);
    }

    @Test(dataProvider = "getBookingCerebraProcessed")
    public void verifyBookingCerebraProcessedTest(JsonObject jsonObject) {
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        removeEndpointFromJsonObject(jsonObject);

        Response response = getJsonSuccessResponse(bookingUrl);

        Booking[] bookings = getBookingsFromResponse(response);
        String bookingId = null;
        for (Booking b : bookings){
            if (b.getRsoProcessed()) {
                bookingId = b.getBookingId();
                break;
            }
        }

        JsonObject body = new JsonObject();
        body.addProperty(BOOKING_ID, bookingId);

        getJsonSuccessResponse(endpoint, body);
    }

    @DataProvider
    public Object[][] getBookingCustomerEdit() {
        return testHelper.getApiCalls("booking_customer_edit", bookingApiFile);
    }

    @Test(dataProvider = "getBookingCustomerEdit")
    public void verifyBookingCustomerEditTest(JsonObject jsonObject) {
        Response response = getJsonSuccessResponse(bookingUrl);

        Booking[] bookings = getBookingsFromResponse(response);

        String bookingId = null;
        String userId = null;
        for (Booking b : bookings){
            if (b.getRsoProcessed()) {
                bookingId = b.getBookingId();
                userId = b.getUserId();
                break;
            }
        }
        LOG.info("bookingId: {}", bookingId);
        LOG.info("userId:    {}", userId);

        JsonObject loginJson = testHelper.getApiCallAsJson("account_user_login", accountApiFile);
        JsonObject loginJsonBody = testHelper.getPostBodyAsJson(loginJson);

        response = getJsonSuccessResponse(loginUrl, loginJsonBody);
        LOG.info("{}: {}", STORM_SESSION, getStormSession(response));

        Header header = new Header(STORM_SESSION, getStormSession(response));

        String endpoint = testHelper.getPostEndpoint(jsonObject);

        JsonObject body = testHelper.getPostBodyAsJson(jsonObject);
        body.addProperty(USER_ID, userId);
        body.addProperty(ID, bookingId);
        LOG.info("POST BODY: {}", body.toString());

        response = getResponse(endpoint, body, header);
        response.prettyPrint();

    }

    @DataProvider
    public Object[][] getBookingHelpdeskEdit() {
        return testHelper.getApiCalls("booking_helpdesk_edit", bookingApiFile);
    }

    @Test(dataProvider = "getBookingHelpdeskEdit")
    public void verifyBookingHelpdeskEditTest(JsonObject jsonObject) {
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        JsonObject body = testHelper.getPostBodyAsJson(jsonObject);
        LOG.info("POST BODY: {}", body.toString());

        Response response = getResponse(endpoint, body);
        response.prettyPrint();
    }


    @DataProvider
    public Object[][] getBookingHelpdeskReservation() {
        return testHelper.getApiCalls("booking_helpdesk_reservation", bookingApiFile);
    }

    @Test(dataProvider = "getBookingHelpdeskReservation")
    public void verifyBookingHelpdeskReservationTest(JsonObject jsonObject) {
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        JsonObject body = testHelper.getPostBodyAsJson(jsonObject);
        LOG.info("POST BODY: {}", body.toString());

        Response response = getResponse(endpoint, body);
        response.prettyPrint();
    }


    @DataProvider
    public Object[][] getBookingXavierState() {
        return testHelper.getApiCalls("booking_xavier_state", bookingApiFile);
    }

    @Test(dataProvider = "getBookingXavierState")
    public void verifyBookingXavierStateTest(JsonObject jsonObject) {
        String endpoint = testHelper.getPostEndpoint(jsonObject);
        String bookingId = getBookingIdRsoProcessed(bookingUrl);

        JsonObject body = new JsonObject();
        body.addProperty(BOOKING_ID, bookingId);
        body.addProperty("state", "FAILED");

        Response response = getJsonSuccessResponse(endpoint, body);
        response.prettyPrint();
    }

    @Test
    public void verifyBookingSuccessTest(){
        throw new NotImplementedException();
    }
}