package tests.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.Booking;

import static io.restassured.RestAssured.given;

public class BookingApiTestHelper {

    Response getJsonResponse(String endpoint) {
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

    Booking[] getBookingsFromResponse(Response response) {
        return response
                .jsonPath()
                .getObject("bookings", Booking[].class);
    }
}
