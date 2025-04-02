package auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigReader;

import static io.restassured.RestAssured.*;

public class LogoutAutomation {

    public static void main(String[] args) {
        // Load the token from the config file
        String token = ConfigReader.getToken();

        if (token != null) {
            // Set the base URI for the API
            RestAssured.baseURI = "https://ramandoauth.ramandtech.com/OAuthAPI/v1";

            // Step: Send the POST request for logout with the Bearer token in the Authorization header
            Response response = given()
                    .header("Authorization", "Bearer " + token)  // Set the Authorization header
                    .when()
                    .post("/Logout")
                    .then()
                    .statusCode(200) // Adjust status code as needed
                    .extract()
                    .response();

            // Print the response body for debugging
            System.out.println("Logout Response Body:");
            response.prettyPrint();

            // Check the response and print whether the logout was successful
            if (response.statusCode() == 200) {
                System.out.println("Logged out successfully!");
            } else {
                System.out.println("Logout failed. Status code: " + response.statusCode());
            }
        } else {
            System.out.println("Token not found. Cannot logout.");
        }
    }
}
