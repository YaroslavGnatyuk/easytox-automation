package com.easytox.automation.steps.lab_pathologist.ET_001;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class LabPathologistAddSteps {
    private WebDriver driver;
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";

    private Logger log = Logger.getLogger(LabPathologistAddSteps.class);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Select Settings -> Lab Pathologist.$")
    public void gotToPathologistPage() {
        try {
            Thread.sleep(1000);
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
//            log.info("page is " + pageTitle);
            assertTrue(pageTitle.equals("Lab Pathologist List"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click Add New Pathologist icon.$")
    public void clickOnAddPathologist() {
        try {
            driver.findElement(By.cssSelector(WElement.createNewPathologistButton)).click();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Add Lab Pathologist' screen is displayed.$")
    public void checkIsItPathologistsAddView() {
        String pageTitle = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText();
//        log.info("page is " + pageTitle);
        assertTrue(pageTitle.equals("Add Lab Pathologist"));
    }

    @When("^Enter all the information in the screen and click Submit.$")
    public void inputAllInformationAndClickSubmit(){
        driver.findElement(By.cssSelector(WElement.pathologistUserNameField)).sendKeys("Famous_pathologist1");
        driver.findElement(By.cssSelector(WElement.pathologistPasswordField)).sendKeys("Welcome@123");
        driver.findElement(By.cssSelector(WElement.pathologistFirstNameField)).sendKeys("Jack");
        driver.findElement(By.cssSelector(WElement.pathologistLastNameField)).sendKeys("Pathologist");
        driver.findElement(By.cssSelector(WElement.pathologistPhoneNumberField)).clear();
        driver.findElement(By.cssSelector(WElement.pathologistPhoneNumberField)).sendKeys("(123)456-7890");
        driver.findElement(By.cssSelector(WElement.pathologistEmailField)).sendKeys("jack_the_pathologist@gmail.com");
        driver.findElement(By.cssSelector(WElement.pathologistSalutationField)).sendKeys("some_salutation");
        driver.findElement(By.cssSelector(WElement.pathologistMedicareNumberField)).sendKeys("21436587");
        driver.findElement(By.cssSelector(WElement.pathologistMedicaidNumberField)).sendKeys("12563478");
        driver.findElement(By.cssSelector(WElement.pathologistUPINNumberField)).sendKeys("0987654321");
        driver.findElement(By.cssSelector(WElement.pathologistStateLicenceField)).sendKeys("Active");
        driver.findElement(By.cssSelector(WElement.pathologistNPIField)).sendKeys("some_npi");
        driver.findElement(By.cssSelector(WElement.pathologistAddSubmit)).click();
    }

    @Then("^New Lab Pathologist is created successfully.$")
    public void isNewPathologistCreate(){
        try {
            Thread.sleep(5000);
            String result = driver.findElement(By.cssSelector("#maincontentdiv > div.page-body > div.alert.alert-success.fade.in > strong"))
                    .getText();

            assertTrue(result.equals("Success"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        driver.close();
    }
}
