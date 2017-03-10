package com.easytox.automation.steps.lab_pathologist.ET_003;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by yroslav on 12/23/16.
 */
public class SearchLabPathologistSteps {
    private WebDriver driver;
    private static final String easyToxAddress = "http://162.243.2.94:8080/easytox/";

    private Logger log = Logger.getLogger(SearchLabPathologistSteps.class);

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.navigate().to(easyToxAddress);
        driver.manage().window().maximize();
    }

    @Given("^User login with the physician \"([^\"]*)\" and password \"([^\"]*)\"$$")
    public void loginToEasyTox(String usr, String psw) {
        try {
            driver.findElement(By.name(WElement.loginPageFieldName)).sendKeys(usr);
            driver.findElement(By.name(WElement.loginPagePasswordField)).sendKeys(psw);
            driver.findElement(By.cssSelector(WElement.loginPageLoginButton)).click();
            Thread.sleep(4000);
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
//            List<String> text = elements.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> text = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            text.add(elements.get(i).getText());
        }
        assertTrue(text.contains(criteria.trim()));
    }


    @After
    public void close() {
        driver.close();
    }
}
