package nl.toefel.location.service.systemtest;

import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class VersionTest extends ApiTestBase {

    @Test
    public void testVersion() {
        RestAssured.get(BASE_URL + "/version")
        .then()
        .statusCode(200)
        .body("application", equalTo("location-service"))
        .body("", hasKey("commitId"))
        .body("", hasKey("buildTimestamp"))
        .body("", hasKey("startTimestamp"))
        .body("", hasKey("currentTime"));
    }
}
