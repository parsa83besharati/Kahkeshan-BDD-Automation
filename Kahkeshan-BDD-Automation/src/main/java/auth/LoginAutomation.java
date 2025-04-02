package auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import utils.ConfigReader;

import static io.restassured.RestAssured.*;

public class LoginAutomation {

    // Create a logger instance using SLF4J
    private static final Logger logger = LoggerFactory.getLogger(LoginAutomation.class);

    public static void main(String[] args) {
        // Load username and password from the config file
        String username = ConfigReader.getUsername();
        String password = ConfigReader.getPassword();

        // Set the base URI for the API
        RestAssured.baseURI = "https://ramandoauth.ramandtech.com/OAuthAPI/v1";

        // Step 1: Send the first POST request (LoginFirstStep) with mobileNumber as username
        logger.info("Starting the login process for username: " + username);

        Response firstResponse = given()
                .contentType("application/json")
                .body("{ \"mobileNumber\": \"" + username + "\" }")
                .when()
                .post("/LoginFirstStep")
                .then()
                .statusCode(200) // Adjust status code as needed
                .extract()
                .response();

        // Print the first response body for debugging
        logger.debug("First Response Body: ");
        firstResponse.prettyPrint();

        // Extract nextStepToken from the first response
        JsonPath firstResponseJson = firstResponse.jsonPath();
        String nextStepToken = firstResponseJson.getString("result.nextStepToken");

        if (nextStepToken != null) {
            logger.info("Extracted nextStepToken: " + nextStepToken);

            // Step 2: Send the second POST request (PasswordLogin) with nextStepToken and password
            Response secondResponse = given()
                    .contentType("application/json")
                    .body("{ \"hasPassToken\": \"" + nextStepToken + "\", \"password\": \"" + password + "\" }")
                    .when()
                    .post("/PasswordLogin")
                    .then()
                    .statusCode(200) // Adjust status code as needed
                    .extract()
                    .response();

            // Print the second response body for debugging
            logger.debug("Second Response Body: ");
            secondResponse.prettyPrint();

            // Extract token from the second response
            JsonPath secondResponseJson = secondResponse.jsonPath();
            String token = secondResponseJson.getString("result.token");

            if (token != null) {
                logger.info("Logged in successfully! Token: " + token);
                // Optionally, store the token in config.properties for use later
                ConfigReader.saveToken(token);
            } else {
                logger.error("Token not found in the second response.");
            }
        } else {
            logger.error("nextStepToken not found in the first response.");
        }
    }
}
