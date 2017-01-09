package com.easytox.automation.steps.security.password_reset.case_6;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
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
    private static final String pageAfterSuccessfulLogging = "http://bmtechsol.com:8080/easytox/lab/payment"; //This address we have after successful logging
    private static final String forgotPasswordScreenAddress = "http://bmtechsol.com:8080/easytox/actionItem/forgotPassword";
    private static final String validEmailAddress = "someEmail@gmail.com";

    private WebElement allLabClientData = null;

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
            assertTrue(driver.getCurrentUrl().equals(pageAfterSuccessfulLogging));
            Thread.sleep(1000);
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

    @And("^Forgot Password   \"([^\"]*)\" <link>$")
    public void isForgotPasswordShowed(String labClient) {

        String request = "ForgotPassword " + labClient;
        List<WebElement> li = driver.findElements(By.cssSelector(WElement.userRequestLine));
        Optional<String> result = li.stream()
                .map(e -> e.getText().replace("\n", " "))
                .filter(e -> e.equals(request))
                .findAny();

        if (!result.isPresent()) {
            assertTrue(isExistLabClient(labClient));
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

    @When("^Select \"See All Requests\".$")
    public void selectAllRequest(){
        driver.findElement(By.linkText(WElement.textLinkSeeAllRequest)).click();
    }

    @Then("^Password Request List\" screen should be displayed.$")
    public void isPasswordRequestListShowed(){
        try {
            Thread.sleep(1000);
            String tableNameShouldBe = "Request List";
            String tableNameWeHave = driver.findElement(By.cssSelector(WElement.tableName)).getText();

            assertTrue(tableNameWeHave.equals(tableNameShouldBe));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Verify the details displayed in \"Password Request List\" screen.$")
    public void checkDisplayPasswordRequestScreen(){
        String titleTextWeHave = driver.findElement(By.cssSelector(WElement.titleHeader)).getText();
        String titleTextShouldBe = "Password Request List";
        assertTrue(titleTextWeHave.equals(titleTextShouldBe));
    }

    @Then("^Following values should be displayed against each PasswordReset Request: Request$")
    public void isColumnRequestShowed(){
        boolean isRequestColumnShowed = driver.findElement(By.cssSelector(WElement.mainTable_requestColumn)).isDisplayed();
        assertTrue(isRequestColumnShowed);
    }
    @And("Requested by")
    public void isColumnRequestByShowed(){
        boolean isColumnRequestByShowed = driver.findElement(By.cssSelector(WElement.mainTable_requestByColumn)).isDisplayed();
        assertTrue(isColumnRequestByShowed);
    }

    @And("^Requested Date$")
    public void isColumnRequestDateShowed(){
        boolean isColumnRequestDateShowed = driver.findElement(By.cssSelector(WElement.mainTable_requestDateColumn)).isDisplayed();
        assertTrue(isColumnRequestDateShowed);
    }
    @And("^Requested Email$")
    public void isColumnRequestEmailShowed(){
        boolean isColumnRequestEmailShowed = driver.findElement(By.cssSelector(WElement.mainTable_requestEmailColumn)).isDisplayed();
        assertTrue(isColumnRequestEmailShowed);
    }
    @And("^Resolved by$")
    public void isColumnRequestResolvedByShowed(){
        boolean isColumnRequestResolvedByShowed = driver.findElement(By.cssSelector(WElement.mainTable_resolvedByColumn)).isDisplayed();
        assertTrue(isColumnRequestResolvedByShowed);
    }
    @And("^Status$")
    public void isColumnRequestStatusShowed(){
        boolean isColumnRequestStatusShowed = driver.findElement(By.cssSelector(WElement.mainTable_requestStatusColumn)).isDisplayed();
        assertTrue(isColumnRequestStatusShowed);
    }
    @And("^Action$")
    public void isColumnRequestActionShowed(){
        boolean isColumnRequestActionShowed = driver.findElement(By.cssSelector(WElement.mainTable_requestActionColumn)).isDisplayed();
        assertTrue(isColumnRequestActionShowed);
    }

    @After
    public void close(){
        driver.close();
    }

    private boolean isExistLabClient(String labClient) {
        try {
            driver.findElement(By.linkText(WElement.textLinkSeeAllRequest)).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.searchField)).sendKeys(labClient);
            Thread.sleep(1000);
            WebElement rowWithResult = driver.findElement(By.cssSelector(WElement.firstRowInResultOfSearch));
            return rowWithResult != null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
