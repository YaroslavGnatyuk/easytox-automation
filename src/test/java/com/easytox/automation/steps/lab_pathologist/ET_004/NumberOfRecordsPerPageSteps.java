package com.easytox.automation.steps.lab_pathologist.ET_004;

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
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class NumberOfRecordsPerPageSteps {
    private WebDriver driver;
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";

    private Logger log = Logger.getLogger(NumberOfRecordsPerPageSteps.class);

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

    @When("^Verify the default number of records displayed$")
    public void verifyTheDefaultNumberOfRecords(){
        assertTrue(driver.findElement(By.cssSelector(WElement.pathologistPerPage)).isDisplayed());
    }

    @Then("^Default number 10 should be displayed in the dropdown box$")
    public void checkDefaultNumber(){
        String defaultNumber =new Select(driver.findElement(By.cssSelector(WElement.pathologistPerPage)))
                .getFirstSelectedOption()
                .getText();
//        log.info("default number is " + defaultNumber);
        assertTrue(defaultNumber.equals("10"));
    }

    @When("^Click on dropdown that shows no of records to be displayed on the page$")
    public void clickOnDropdown(){
        driver.findElement(By.cssSelector(WElement.pathologistPerPage)).click();
    }

    @Then("^User should be able to view and select the options from the list and the corresponding number of records should be displayed on the page.$")
    public void checkViewAndSelectOptionFromTheDropdown(){
        String[] dropDownList = {"10","25","50","100","All"};
        checkViewOption(dropDownList);

        for (int i = 0; i < dropDownList.length; i++) {
            checkSelectOption(dropDownList[i]);
        }
    }

    @After
    public void close(){
        driver.close();
    }

    private void checkViewOption(String[] dropDownList){
        List<String> originalDropDownList = Arrays.asList(driver
                .findElement(By.cssSelector(WElement.pathologistPerPage))
                .getText()
                .split("\\n"));

        for (int i = 0; i < dropDownList.length; i++) {
            assertTrue(originalDropDownList.get(i).equals(dropDownList[i]));
        }
    }

    private void checkSelectOption(String selectValue) {
        switch (selectValue){
            case "10":{
                new Select(driver.findElement(By.cssSelector(WElement.pathologistPerPage))).selectByVisibleText(selectValue);
                List<WebElement> webElements = driver.findElements(By.cssSelector(WElement.oneRowInMainTable));
                assertTrue(webElements.size() <= Integer.valueOf(selectValue));
                log.info(selectValue);
                break;
            }

            case "25":{
                new Select(driver.findElement(By.cssSelector(WElement.pathologistPerPage))).selectByVisibleText(selectValue);
                List<WebElement> webElements = driver.findElements(By.cssSelector(WElement.oneRowInMainTable));
                assertTrue(webElements.size() <= Integer.valueOf(selectValue));
                log.info(selectValue);
                break;
            }

            case "50":{
                new Select(driver.findElement(By.cssSelector(WElement.pathologistPerPage))).selectByVisibleText(selectValue);
                List<WebElement> webElements = driver.findElements(By.cssSelector(WElement.oneRowInMainTable));
                assertTrue(webElements.size() <= Integer.valueOf(selectValue));
                log.info(selectValue);

                break;
            }

            case "100":{
                new Select(driver.findElement(By.cssSelector(WElement.pathologistPerPage))).selectByVisibleText(selectValue);
                List<WebElement> webElements = driver.findElements(By.cssSelector(WElement.oneRowInMainTable));
                assertTrue(webElements.size() <= Integer.valueOf(selectValue));

                break;
            }

            case "All":{
                new Select(driver.findElement(By.cssSelector(WElement.pathologistPerPage))).selectByVisibleText(selectValue);
                List<WebElement> webElements = driver.findElements(By.cssSelector(WElement.oneRowInMainTable));
                String amountOfPathologist = driver.findElement(By.cssSelector(WElement.amountOfPathologists)).getText().split(" ")[5];
                assertTrue(webElements.size() == Integer.valueOf(amountOfPathologist));
                log.info(selectValue);

                break;
            }

            default:{
                log.info("There is error in select");
            }
        }
    }
}
