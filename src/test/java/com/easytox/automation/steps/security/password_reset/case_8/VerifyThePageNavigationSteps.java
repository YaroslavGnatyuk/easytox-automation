package com.easytox.automation.steps.security.password_reset.case_8;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class VerifyThePageNavigationSteps {
    private WebDriver driver;
    private Logger log = Logger.getLogger(VerifyThePageNavigationSteps.class);
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String pageAfterSuccessfulLogging = "http://bmtechsol.com:8080/easytox/lab/payment"; //This address we have after successful logging

    private int numberOfActivePage;

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

    @When("^Navigate back and forth by selecting page numbers (.*)$")
//todo I should remake this method. I should add checking for 1,2,3 and Prev buttons
    public void selectPageByButton(String button) {
        try {
            Thread.sleep(1000);

            switch (button) {

                /**I click "3" button before I click "Prev" because at the beginning "Prev" doesn't active**/
                case "Prev": {
                    driver.findElement(By.cssSelector(WElement.paginationThirdPage)).click();
                    driver.findElement(By.cssSelector(WElement.paginationPrevButton)).click();
                    break;
                }

                case "1": {
                    driver.findElement(By.cssSelector(WElement.paginationFirstPage)).click();
                    break;
                }

                case "2": {
                    driver.findElement(By.cssSelector(WElement.paginationSecondPage)).click();
                    break;
                }

                case "3": {
                    driver.findElement(By.cssSelector(WElement.paginationThirdPage)).click();
                    break;
                }

                case "Next": {
                    driver.findElement(By.cssSelector(WElement.paginationNextButton)).click();
                    break;
                }

                default:
                    log.info("This type doesn't exist!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^User should be navigate to the selected page(.*)$")
    public void checkSelectedPage(String button) {
        numberOfActivePage = getActivePage();
        switch (button) {
            case "Next": {
                Assert.assertTrue(numberOfActivePage == 2);
                break;
            }
            case "1": {
                Assert.assertTrue(numberOfActivePage == 1);
                break;
            }
            case "2": {
                Assert.assertTrue(numberOfActivePage == 2);
                break;
            }
            case "3": {
                Assert.assertTrue(numberOfActivePage == 3);
                break;
            }
            case "Prev": {
                Assert.assertTrue(numberOfActivePage == 2);
                break;
            }
            default:
                log.info("This type doesn't exist!");
        }
    }

    @When("^Checking the message of no of records displayed on the current page bottom left corner of the screen$")
    public void checkTheMessage() {
        boolean isShowedTheMessage = driver.findElement(By.cssSelector(WElement.bottomTextMessage)).isDisplayed();
        Assert.assertTrue(isShowedTheMessage);
    }

    @Then("^A text message “Showing x to y of z entries” should be displayed on the bottom left corner of the list.$")
    public void checkTextOfTheMessage() {
        String theMessageShouldBe = "Showing x to y of z entries";
        String theMessageWeHave = driver.findElement(By.cssSelector(WElement.bottomTextMessage)).getText();
        String[] temp = theMessageWeHave.split(" ");

        theMessageWeHave = temp[0] + " x " + temp[2] + " y " + temp[4] + " z " + temp[6];

        Assert.assertTrue(theMessageWeHave.equals(theMessageShouldBe));
    }

    @After
    public void close() {
        driver.close();
    }

    private int getActivePage() {
        List<WebElement> paginationPages = driver.findElements(By.cssSelector(WElement.cssSelectorForPaginationButton));
        int activePage = 0;

        for (WebElement pageNumber : paginationPages) {
            if (pageNumber.getAttribute("class").equals("active")) {
                activePage = Integer.valueOf(pageNumber.findElement(By.cssSelector(WElement.thisIsActivePage)).getText());
            }
        }
        return activePage;
    }
}
