package tests.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

public abstract class AbstractApiTest {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractApiTest.class);

    protected final RestApiTestHelper testHelper = new RestApiTestHelper();

    protected void removeEndpointFromJsonObject(JsonObject jsonObject) {
        jsonObject.remove("endpoint");
    }

    protected String getStormSession(Response response) {
        return response.path("stormSession");
    }

    protected String getUserId(Response response) {
        return response.path("userId");
    }

    protected String getEmail(Response response) {
        return response.path("email");
    }

    protected String getBookingId(Response response) {
        return response.path("bookingId");
    }

    protected String getId(Response response) {
        return response.path("id");
    }



    protected Map<String, Object> getPostBodyAsMap(JsonObject jsonObject) {
        // create a POST body
        Map<String, Object> jsonBody = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();

        for(Map.Entry<String,JsonElement> entry : entrySet){
            jsonBody.put(entry.getKey(), jsonObject.get(entry.getKey()));
            LOG.info("API CALL POST BODY: " +entry.getKey()+ " : " + jsonObject.get(entry.getKey()));
        }
        return jsonBody;
    }

    protected Response getResponse(String endpoint) {
        return given()
                .when()
                .get(endpoint)
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }

    protected Response getResponse(String endpoint, JsonObject jsonBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }


    protected Response getResponse(String endpoint, JsonObject jsonBody, Header header) {
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

    protected Response getJsonSuccessResponse(String endpoint) {
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

    protected Response getJsonSuccessResponse(String endpoint, Map<String, Object> jsonBody) {
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

    protected Response getJsonSuccessResponse(String endpoint, JsonObject jsonBody) {
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

    protected Response getJsonSuccessResponse(String endpoint, Headers headers) {
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

    protected Response getJsonSuccessResponse(String endpoint, Header header) {
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

    protected Response getJsonSuccessResponse(String endpoint, JsonObject jsonBody, Header header) {
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
