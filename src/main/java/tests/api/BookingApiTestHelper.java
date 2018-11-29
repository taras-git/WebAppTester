package tests.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.Booking;

import java.util.Map;

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

    Response getJsonResponse(String endpoint, Map<String, Object> jsonBody) {
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

    Booking[] getBookingsFromResponse(Response response) {
        return response
                .jsonPath()
                .getObject("bookings", Booking[].class);
    }


}
