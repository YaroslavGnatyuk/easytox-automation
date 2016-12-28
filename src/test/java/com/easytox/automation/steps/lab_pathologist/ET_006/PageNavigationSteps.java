package com.easytox.automation.steps.lab_pathologist.ET_006;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PageNavigationSteps {
    private WebDriver driver;
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";
    private Logger log = Logger.getLogger(PageNavigationSteps.class);

    private int numberOfActivePageBefore;
    private int numberOfActivePageAfter;

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.SECONDS);
    }

    @Given("^User login with the physician \"([^\"]*)\" and password \"([^\"]*)\"$$")
    public void loginToEasyTox(String usr, String psw) {
        try {
            driver.navigate().to(easyToxAddress);
            driver.findElement(By.name(WElement.loginPageFieldName)).sendKeys(usr);
            driver.findElement(By.name(WElement.loginPagePasswordField)).sendKeys(psw);
            driver.findElement(By.cssSelector(WElement.loginPageLoginButton)).click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Select Settings -> Lab Pathologist.$")
    public void gotToPathologistPage() {
        try {
            driver.findElement(By.cssSelector(WElement.dropdownToggle)).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.menuIconLabPathologist)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Lab Pathologist List screen is displayed.$")
    public void checkIsItPathologistPage() {
        try {
            Thread.sleep(1000);
            String pageTitle = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText();
            assertTrue(pageTitle.equals("Lab Pathologist List"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Navigate back and forth by selecting page numbers \"Prev/1,2,3/Next\"$")
    public void selectPageNumber() {
        try {
            Thread.sleep(1000);

            List<WebElement> elements = getAllElementsFromPagination();
            numberOfActivePageBefore = getActivePage(elements);

            driver.findElement(By.cssSelector(WElement.paginationNextButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^User should be navigate to the selected page$")
    public void checkNumberOfThePage() {
        List<WebElement> elements = getAllElementsFromPagination();
        numberOfActivePageAfter = getActivePage(elements);

        assertFalse(numberOfActivePageBefore == numberOfActivePageAfter);
    }

    @When("^Checking the message of no of records displayed on the current page bottom left corner of the screen$")
    public void checkTheMessage() {
        boolean isShowedTheMessage = driver.findElement(By.cssSelector(WElement.bottomTextMessage)).isDisplayed();
        assertTrue(isShowedTheMessage);
    }

    @Then("^A text message “Showing x to y of z entries” should be displayed on the bottom left corner of the list.$")
    public void checkTextOfTheMessage() {
        String theMessageShouldBe = "Showing x to y of z entries";
        String theMessageWeHave = driver.findElement(By.cssSelector(WElement.bottomTextMessage)).getText();
//        log.info("We have " + theMessageWeHave + "\n");
//        log.info("should be " + theMessageShouldBe + "\n");

        String[] temp = theMessageWeHave.split(" ");
        theMessageWeHave = temp[0] + " x " + temp[2] + " y " + temp[4] + " z " + temp[6];

        assertTrue(theMessageWeHave.equals(theMessageShouldBe));
    }

    private List<WebElement> getAllElementsFromPagination() {
        return driver
                .findElements(By.cssSelector(WElement.pagination));
    }

    private int getActivePage(List<WebElement> webElements) {
        int activePage = 0;
        for (WebElement webElement : webElements) {
            try {
                if (webElement.findElement(By.cssSelector(WElement.thisIsActivePage)).isDisplayed()) {
                    activePage = Integer.valueOf(webElement
                            .findElement(By.cssSelector(WElement.thisIsActivePage))
                            .getText());
                }
            } catch (NoSuchElementException ignored) {
            }
        }

        return activePage;
    }

    @After
    public void close() {
        driver.close();
    }
}
