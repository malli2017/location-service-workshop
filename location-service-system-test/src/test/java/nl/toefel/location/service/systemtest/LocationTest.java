package nl.toefel.location.service.systemtest;

import io.restassured.http.ContentType;
import nl.toefel.location.service.systemtest.dto.LocationDTO;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class LocationTest extends ApiTestBase {

    @Test
    public void testCRUD() {
        String locationName = "test-location-1";

        // delete test-data to make sure you start with a clean slate.
        // deleting before instead of after the test also helps while debugging after failed tests
        delete(BASE_URL + "/v1/locations/" + locationName).then().statusCode(anyOf(equalTo(200), equalTo(404)));

        // save a new one
        given()
                .contentType(ContentType.JSON)
                .body(new LocationDTO(locationName, 1.25, 2.23))
                .when()
                .post(BASE_URL + "/v1/locations")
                .then()
                .statusCode(200);

        // retrieve it again
        get(BASE_URL + "/v1/locations/" + locationName)
                .then()
                .statusCode(200)
                .body("name", equalTo(locationName));

    }


}

