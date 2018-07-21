package nl.toefel.location.service.systemtest;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;

import static io.restassured.RestAssured.get;

/**
 * Class that contains base values and methods that other test-classes can reuse
 */
public abstract class ApiTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(ApiTestBase.class);
    private static final Config CONFIG = Config.fromEnvironment();

    static final String BASE_URL = "http://" + CONFIG.getLocationServiceAddress();

    @BeforeClass
    public static void waitForApplicationToBeReady() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            try {
                get(BASE_URL + "/version").then().statusCode(200);
                LOG.info("Application responded with 200 on /version, assuming it's UP.");
            } catch(Exception e) {
                // java does not allow me to catch ConnectException, because it's not thrown 'supposedly'
                // but it IS thrown -_-
                if (e instanceof ConnectException) {
                    LOG.info("Application did not respond with 200 on /version, assuming it's not up yet.");
                    Thread.sleep(1000);
                    continue;
                }
            }
            break;
        }
    }
}
