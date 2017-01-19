package com.easytox.automation.steps.security.password_reset.case_9;

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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class VerifyTheSearchResultSteps {
    private WebDriver driver;
    private Logger log = Logger.getLogger(VerifyTheSearchResultSteps.class);
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String pageAfterSuccessfulLogging = "http://bmtechsol.com:8080/easytox/lab/payment"; //This address we have after successful logging

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

    @And("^Select \"See All Requests\".$")
    public void selectAllRequest() {
        driver.findElement(By.linkText(WElement.textLinkSeeAllRequest)).click();
    }

    @Then("^Password Request List\" screen should be displayed.$")
    public void isPasswordRequestListShowed() {
        try {
            Thread.sleep(1000);
            String tableNameShouldBe = "Request List";
            String tableNameWeHave = driver.findElement(By.cssSelector(WElement.tableName)).getText();

            assertTrue(tableNameWeHave.equals(tableNameShouldBe));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @When("^Enter any search criteria (.*) and click on search icon.$")
    public void enterAnySearchParameters(String criteria) {
        driver.findElement(By.cssSelector(WElement.mainSearchField)).sendKeys(criteria);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Matching records with entered data(.*) should be displayed in the Lab Pathologist List.$")
    public void matchingData(String criteria) {
        List<WebElement> elements = driver
                .findElement(By.cssSelector(WElement.resultSearching))
                .findElements(By.cssSelector(WElement.oneRowInResultSearching));
        List<String> text = elements.stream().map(WebElement::getText).collect(Collectors.toList());

        assertTrue(text.contains(criteria.trim()));
    }

    @After
    public void close() {
        driver.close();
    }
}
