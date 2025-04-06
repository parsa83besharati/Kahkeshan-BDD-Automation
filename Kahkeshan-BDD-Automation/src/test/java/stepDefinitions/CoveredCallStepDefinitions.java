package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("Covered Call Strategy")
@Feature("Covered Call BEP Calculation")
public class CoveredCallStepDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(CoveredCallStepDefinitions.class);
    private Response response;

    @When("I Call Covered Call Strategy API With BestLimitPrice Price Type , BestLimit Symbol Basis And With Commission")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Call API with commission = true")
    public void callWithCommission() {
        String token = ConfigReader.getToken();
        String baseUrl = ConfigReader.getBaseURL() + "true";

        Allure.step("Sending GET request with commission = true to: " + baseUrl);

        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(baseUrl);

        Allure.step("Response status code: " + response.getStatusCode());
        logger.info("✅ API called | Status Code: {}", response.getStatusCode());
    }

    @When("I Call Covered Call Strategy API With BestLimitPrice Price Type , BestLimit Symbol Basis And Without Commission")
    @Severity(SeverityLevel.CRITICAL)
    @Step("Call API with commission = false")
    public void callWithoutCommission() {
        String token = ConfigReader.getToken();
        String baseUrl = ConfigReader.getBaseURL() + "false";

        Allure.step("Sending GET request with commission = false to: " + baseUrl);

        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(baseUrl);

        Allure.step("Response status code: " + response.getStatusCode());
        logger.info("✅ API called | Status Code: {}", response.getStatusCode());
    }

    @Then("^The Covered Call Strategy BEP Of (.+) With Commission Is Valid$")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validates BEP of Covered Call Strategy with commission for given symbol")
    @Step("Validate BEP with commission for symbol: {baseSymbol}")
    public void validateBEPWithCommission(String baseSymbol) {
        validateBEP(baseSymbol, true);
    }

    @Then("^The Covered Call Strategy BEP Of (.+) Without Commission Is Valid$")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validates BEP of Covered Call Strategy without commission for given symbol")
    @Step("Validate BEP without commission for symbol: {baseSymbol}")
    public void validateBEPWithoutCommission(String baseSymbol) {
        validateBEP(baseSymbol, false);
    }

    @Step("Validating BEP for symbol: {baseSymbol}, WithCommission: {withCommission}")
    private void validateBEP(String baseSymbol, boolean withCommission) {
        List<Map<String, Object>> resultList = response.jsonPath().getList("result");
        Allure.step("Extracted result list with size: " + (resultList != null ? resultList.size() : 0));

        boolean symbolFound = false;

        for (Map<String, Object> item : resultList) {
            String responseBaseSymbol = item.get("baseSymbolTitle").toString().trim();

            if (responseBaseSymbol.equals(baseSymbol.trim())) {
                symbolFound = true;

                double basePrice = Double.parseDouble(item.get("baseLastTradedPrice").toString());
                double optionBuyPrice = Double.parseDouble(item.get("optionBestBuyLimitPrice").toString());
                double strikePrice = Double.parseDouble(item.get("strikePrice").toString());
                double expectedBep = Double.parseDouble(item.get("coveredCallBEP").toString());

                Allure.step(String.format("basePrice: %.2f, optionBuyPrice: %.2f, strikePrice: %.2f, expectedBEP: %.2f",
                        basePrice, optionBuyPrice, strikePrice, expectedBep));

                double commissionBuy = withCommission ? 0.003712 : 0.0;
                double commissionSellCall = withCommission ? 0.00103 : 0.0;
                double commissionStrike = withCommission ? 0.0005 : 0.0;
                double taxSell = withCommission ? 0.005 : 0.0;

                double calculatedBep = (basePrice + basePrice * commissionBuy)
                        - (optionBuyPrice - optionBuyPrice * commissionSellCall)
                        + (strikePrice * commissionStrike)
                        + (strikePrice * taxSell);

                double difference = Math.abs(calculatedBep - expectedBep);

                logger.info("🔍 {} => Calculated BEP: {}, Expected BEP: {}, Difference: {}",
                        baseSymbol, calculatedBep, expectedBep, difference);

                Allure.step(String.format("Calculated BEP: %.4f, Expected BEP: %.4f, Difference: %.4f",
                        calculatedBep, expectedBep, difference));

                assertTrue(difference < 10, "❌ BEP difference is too high for symbol: " + baseSymbol);
                logger.info("✅ BEP validated successfully for {}", baseSymbol);
                Allure.step("✅ BEP validated successfully for " + baseSymbol);
                break;
            }
        }

        assertTrue(symbolFound, "❌ Symbol " + baseSymbol + " not found in API response.");
        if (!symbolFound) {
            Allure.step("❌ Symbol not found: " + baseSymbol);
        }
    }
}
