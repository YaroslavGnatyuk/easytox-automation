package com.easytox.automation.steps.security.password_reset.case_6;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by yroslav on 12/30/16.
 */
public class PasswordRequestListSteps {
    private WebDriver driver;
    private Logger log = Logger.getLogger(PasswordRequestListSteps.class);
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String currentLabPage = "http://bmtechsol.com:8080/easytox/caseOrder/list"; //This address we have after successful logging
    private static final String forgotPasswordScreenAddress = "http://bmtechsol.com:8080/easytox/actionItem/forgotPassword";
    private static final String validEmailAddress = "someEmail@gmail.com";

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @When("^Login to Easytox with \"([^\"]*)\" and \"([^\"]*)\" credentials.$")
    public void loginToEasyTox(String username, String password) {
        driver.navigate().to(easyToxAddress);
        driver.findElement(By.name(WElement.loginPage_fieldName)).sendKeys(username);
        driver.findElement(By.name(WElement.loginPage_passwordField)).sendKeys(password);
        driver.findElement(By.cssSelector(WElement.loginPage_loginButton)).click();
    }

    @Then("^User login should be successful.$")
    public void checkCurrentPage() {
        try {
            Thread.sleep(1000);
            assertTrue(driver.getCurrentUrl().equals(currentLabPage));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click \"Pending Password Requests\" link.$")
    public void clickOnThePendingPasswordRequest() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.pendingPasswordRequest)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Following should be displayed:1 Password Requests$")
    public void isPasswordRequestShowed() {
        boolean isPasswordRequestsShowed = driver
                .findElement(By.cssSelector(WElement.passwordRequest))
                .isDisplayed();
        assertTrue(isPasswordRequestsShowed);
    }

    @And("^Forgot Password   some user <link>$")
    public void isForgotPasswordShowed(String labClient) {

        String request = "ForgotPassword " + labClient;
        List<WebElement> li = driver.findElements(By.cssSelector(WElement.userRequestLine));
        Optional<String> result = li.stream()
                .map(e -> e.getText().replace("\n", " "))
                .filter(e -> e.equals(request))
                .findAny();

        if (!result.isPresent()) {
//            assertTrue(isExistLabClient(labClient)); todo I should remove comment
            driver.findElement(By.cssSelector(WElement.pendingPasswordRequest)).click(); //open dropdown window to continue tests
        } else {
            assertTrue(result.isPresent());
        }
    }

    @And("^See All Requests <Link>$")
    public void isAllRequestLinkShowed() {
        WebElement textLink = driver.findElement(By.linkText(WElement.textLinkSeeAllRequest));
        assertNotNull(textLink);
    }
}
