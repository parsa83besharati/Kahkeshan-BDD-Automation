package runners;

import auth.LoginAutomation;
import auth.LogoutAutomation;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

@CucumberOptions(
        features = "features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" // removes hardcoded json/html plugins
        }
)

public class TestRunner extends AbstractTestNGCucumberTests {

        @BeforeTest
        public void loginBeforeTests() {
                System.out.println("🔥 Running login before tests...");
                LoginAutomation.main(null); // Calls login before tests
        }

        @AfterTest
        public void logoutAfterTests() {
                System.out.println("🚀 Running logout after tests...");
                LogoutAutomation.main(null); // Calls logout after tests
        }
}
