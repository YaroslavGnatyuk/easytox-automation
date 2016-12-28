package com.easytox.automation.steps.security.password_reset.case_1;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class PasswordResetPageSteps {
    private WebDriver driver;
    private Logger log = Logger.getLogger(PasswordResetPageSteps.class);
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String forgotPasswordScreenAddress = "http://bmtechsol.com:8080/easytox/actionItem/forgotPassword";

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @When("^Launch application using link: \"([^\"]*)\"$")
    public void goToLoginPage(String address) {
        driver.navigate().to(address);
    }

    @Then("^Easytox Login screen should be displayed.$")
    public void checkCurrentScreen() {
        String headerTextShouldBe = "Sign In";
        String headerTextWeHave = driver.findElement(By.cssSelector(WElement.panelHeading)).getText();
//        log.info(headerTextShouldBe + "\n");
//        log.info(headerTextWeHave + "\n");
        assertTrue(headerTextWeHave.equals(headerTextShouldBe));
    }

    @When("^Verify the text \"Forgot Password. Click here.\"$")
    public void checkText() {
        boolean isFooterDisplayed = driver.findElement(By.cssSelector(WElement.panelFooter)).isDisplayed();

        String bottomTextShouldBe = "Forgot Password? Click here";
        String bottomTextWeHave = driver.findElement(By.cssSelector(WElement.panelFooter)).getText();

        assertTrue(isFooterDisplayed);
        assertTrue(bottomTextWeHave.equals(bottomTextShouldBe));
    }

    @Then("^Text should be displayed with \"click here\" as link.$")
    public void isTextLink() {
        boolean isClickHereReference = driver.findElement(By.linkText("Click here")).isDisplayed();
        assertTrue(isClickHereReference);
    }

    @When("^Click \"Click here\" link.$")
    public void clickOnTextLink() {
        driver.findElement(By.linkText("Click here")).click();
    }

    @Then("^Forgot Password screen should be displayed.$")
    public void checkForgotPasswordScreen() {
        String currentPage = driver.getCurrentUrl();
        assertTrue(currentPage.equals(forgotPasswordScreenAddress));
    }

    @When("^Verify the details displayed in the Forgot Password screen.$")
    public void gotToForgotPasswordPage() {
        if (!driver.getCurrentUrl().equals(forgotPasswordScreenAddress)) {
            driver.navigate().to(easyToxAddress);
            driver.findElement(By.linkText("Click here")).click();
        }

    }

    @Then("^Following should be displayed in the Forgot Password screen:$")
    public void checkAllElements() {

        boolean isForgotTextShowed = driver.findElement(By.cssSelector(WElement.forgotText)).isDisplayed();
        boolean isUsernameFieldShowed = driver.findElement(By.cssSelector(WElement.forgotUsernameField)).isDisplayed();
        boolean isEmailFieldShowed = driver.findElement(By.cssSelector(WElement.forgotEmailField)).isDisplayed();

        assertTrue(isForgotTextShowed && isUsernameFieldShowed && isEmailFieldShowed);
    }

    @After
    public void close() {
        driver.close();
    }
}
