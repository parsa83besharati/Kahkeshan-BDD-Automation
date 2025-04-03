package runners;

import auth.LoginAutomation;
import auth.LogoutAutomation;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import java.io.IOException;

@CucumberOptions(
        features = "features",
        glue = {"stepDefinitions"},
        plugin = {"pretty", "json:target/cucumber/report.json", "html:target/cucumber/report.html"}
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
