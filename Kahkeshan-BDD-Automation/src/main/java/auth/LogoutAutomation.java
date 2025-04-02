package auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigReader;

import static io.restassured.RestAssured.*;

public class LogoutAutomation {

    // Create a logger instance using SLF4J with Log4j2
    private static final Logger logger = LoggerFactory.getLogger(LogoutAutomation.class);

    public static void main(String[] args) {
        // Load the token from the config file
        String token = ConfigReader.getToken();

        if (token != null) {
            // Set the base URI for the API
            RestAssured.baseURI = "https://ramandoauth.ramandtech.com/OAuthAPI/v1";

            // Log the start of the logout process
            logger.info("Starting the logout process with token: {}", token);

            // Step: Send the POST request for logout with the Bearer token in the Authorization header
            Response response = given()
                    .header("Authorization", "Bearer " + token)  // Set the Authorization header
                    .when()
                    .post("/Logout")  // Send the logout request
                    .then()
                    .statusCode(200)  // Expected status code for successful logout
                    .extract()
                    .response();

            // Print the response body for debugging
            logger.debug("Logout Response Body: ");
            response.prettyPrint();

            // Check the response and log whether the logout was successful
            if (response.statusCode() == 200) {
                logger.info("Logged out successfully!");
            } else {
                logger.error("Logout failed. Status code: {}", response.statusCode());
            }
        } else {
            // If no token is found, log an error
            logger.error("Token not found. Cannot logout.");
        }
    }
}
