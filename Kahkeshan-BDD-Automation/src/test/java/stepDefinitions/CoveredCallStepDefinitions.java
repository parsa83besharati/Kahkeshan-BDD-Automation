package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;
import static io.restassured.RestAssured.given;
import org.testng.Reporter;

public class CoveredCallStepDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(CoveredCallStepDefinitions.class);
    private Response response;
    private String token = ConfigReader.getToken();

    // Scenario-level counters
    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;

    // Reset counters before each scenario
    @Before
    public void resetCounters() {
        totalTests = 0;
        passedTests = 0;
        failedTests = 0;
    }

    @Given("I am authenticated")
    public void i_am_authenticated() {
        if (token == null) {
            throw new RuntimeException("Token not found.");
        }
        logger.info("✅ Successfully authenticated!");
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        response = given()
                .baseUri("https://ramandoauth.ramandtech.com/OAuthAPI/v1")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint);

        logger.info("✅ API response received | Status: {}", response.getStatusCode());
    }

    @Then("I verify the coveredCallBEP calculation for {string} with a tolerance of {int}")
    public void i_verify_the_covered_call_BEP_calculation(String marketType, int tolerance) {
        double commission_buy_bourse = 0.003712;
        double commission_buy_faraBourse = 0.003632;
        double commission_sell_call = 0.00103;
        double commission_strike = 0.0005;
        double tax_sell = 0.005;

        response.then().statusCode(200);
        var jsonResponse = response.jsonPath().getList("result");

        // Convert marketType to lowercase to ensure case-insensitive matching
        String lowerCaseMarketType = marketType.toLowerCase();

        for (Object obj : jsonResponse) {
            var item = (java.util.Map<String, Object>) obj;
            String baseMarketUnit = item.get("baseMarketUnit").toString().toLowerCase();

            // Only process if the market type matches and the test is valid for the market
            if (baseMarketUnit.contains(lowerCaseMarketType) && isValidMarketType(lowerCaseMarketType, baseMarketUnit)) {
                totalTests++;  // Only increment totalTests if the market type matches

                double base_price = Double.parseDouble(item.get("baseLastTradedPrice").toString());
                double option_buy_price = Double.parseDouble(item.get("optionBestBuyLimitPrice").toString());
                double strike_price = Double.parseDouble(item.get("strikePrice").toString());
                double expected_bep = Double.parseDouble(item.get("coveredCallBEP").toString());

                double calculated_bep = (base_price + (base_price *
                        (lowerCaseMarketType.equals("farabourse") ? commission_buy_faraBourse : commission_buy_bourse))) -
                        (option_buy_price - (option_buy_price * commission_sell_call)) +
                        (strike_price * commission_strike) + (strike_price * tax_sell);

                double difference = Math.abs(calculated_bep - expected_bep);

                if (difference < tolerance) {
                    passedTests++;
                    logger.info("✅ Test {}: PASSED | Expected: {} | Calculated: {} | Difference: {}",
                            totalTests, expected_bep, calculated_bep, difference);
                } else {
                    failedTests++;
                    logger.info("❌ Test {}: FAILED | Expected: {} | Calculated: {} | Difference: {}",
                            totalTests, expected_bep, calculated_bep, difference);
                }
            } else {
                // Log skipping test with market type info
                logger.info("⏭️ Skipping test for market type [{}] because the response was for [{}].",
                        lowerCaseMarketType, baseMarketUnit);
            }
        }

        // Generate the scenario-specific report
        generateScenarioReport();
    }

    // Validate if the market type is valid for this test case (case-insensitive)
    private boolean isValidMarketType(String marketType, String baseMarketUnit) {
        String lowerCaseBaseMarketUnit = baseMarketUnit.toLowerCase();

        if (marketType.equals("farabourse")) {
            return !(lowerCaseBaseMarketUnit.contains("bourse") && !lowerCaseBaseMarketUnit.contains("farabourse")
                    || lowerCaseBaseMarketUnit.contains("etf"));
        } else if (marketType.equals("bourse")) {
            return !(lowerCaseBaseMarketUnit.contains("farabourse") || lowerCaseBaseMarketUnit.contains("etf"));
        } else if (marketType.equals("etf")) {
            return !(lowerCaseBaseMarketUnit.contains("bourse") || lowerCaseBaseMarketUnit.contains("farabourse"));
        }
        return true; // Default to valid for unknown market types
    }

    // Generate report for each scenario
    private void generateScenarioReport() {
        logger.info("\n📊 SCENARIO EXECUTION SUMMARY:");
        logger.info("🔹 Total Tests Executed: {}", totalTests);
        logger.info("✅ Passed Tests: {}", passedTests);
        logger.info("❌ Failed Tests: {}", failedTests);

        Reporter.log("\n📊 SCENARIO EXECUTION SUMMARY:", true);
        Reporter.log("🔹 Total Tests Executed: " + totalTests, true);
        Reporter.log("✅ Passed Tests: " + passedTests, true);
        Reporter.log("❌ Failed Tests: " + failedTests, true);
    }
}
