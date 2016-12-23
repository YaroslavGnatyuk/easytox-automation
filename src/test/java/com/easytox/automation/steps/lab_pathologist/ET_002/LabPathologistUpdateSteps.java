package com.easytox.automation.steps.lab_pathologist.ET_002;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LabPathologistUpdateSteps {
    private WebDriver driver;
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";

    private Logger log = Logger.getLogger(LabPathologistUpdateSteps.class);

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

    @When("Click Edit icon for an existing lab pathologist.")
    public void clickEditPathologistIcon() {
        driver.findElement(By.cssSelector(WElement.pathologistEditIcon)).click();
    }

    @Then("^Update Lab Pathologist' screen is displayed.$")
    public void checkIsPathologistUpdateScreenShowed() {
        String pageTitle = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText();
        log.info("page title is " + pageTitle + "\n");
        assertTrue(pageTitle.equals("Update Lab Pathologist"));
    }

    @When("^Verify User Information section.$")
    public void verifyUserInformationSection() {
        String pageTitle = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText();
        log.info("page title is " + pageTitle + "\n");
        assertTrue(pageTitle.equals("Update Lab Pathologist"));
    }

    @Then("^User Information should be locked for editing.$")
    public void theFieldsIsLocked() {
        /**Additional variant**/
        /*String userNameFieldTextBefore = driver.findElement(By.cssSelector(WElement.pathologistUpdatePageUserNameField)).getText(); //I save fields text
        String passwordFieldTextBefore = driver.findElement(By.cssSelector(WElement.pathologistUpdatePagePasswordField)).getText();

        driver.findElement(By.cssSelector(WElement.pathologistUpdatePageUserNameField)).sendKeys("newUserName");      //I try to set new text
        driver.findElement(By.cssSelector(WElement.pathologistUpdatePagePasswordField)).sendKeys("newWelcome@123");

        String userNameFieldTextNew = driver.findElement(By.cssSelector(WElement.pathologistUpdatePageUserNameField)).getText(); //I save new text from field
        String passwordFieldTextNew = driver.findElement(By.cssSelector(WElement.pathologistUpdatePagePasswordField)).getText();

        log.info("old username " + userNameFieldTextBefore);
        log.info("old password " + passwordFieldTextBefore);
        log.info("new username " + userNameFieldTextNew);
        log.info("new password " + passwordFieldTextNew);

        assertTrue(userNameFieldTextBefore.equals(userNameFieldTextNew)
                && passwordFieldTextBefore.equals(passwordFieldTextNew));*/

        String isReadonlyUsernameField = driver
                .findElement(By.cssSelector(WElement.pathologistUpdatePageUserNameField))
                .getAttribute("readonly");
        String isReadonlyPasswordField = driver
                .findElement(By.cssSelector(WElement.pathologistUpdatePagePasswordField))
                .getAttribute("readonly");

//        log.info(isReadonlyUsernameField);
//        log.info(isReadonlyPasswordField);

        assertNotNull("This attribute shouldn't be a null", isReadonlyUsernameField);
        assertNotNull(isReadonlyPasswordField);
    }

    @When("^Make changes to Personal/Clinician Information and click update.$")
    public void makeChangeAndClickUpdate() {

        try {
            Thread.sleep(1000);

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageFirstNameField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageFirstNameField)).sendKeys("newPathologistFirstName");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageLastNameField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageLastNameField)).sendKeys("newPathologistLastName");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePagePhoneNumberField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePagePhoneNumberField)).sendKeys("(000)000-0000");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageEmailField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageEmailField)).sendKeys("newPathologist@gmail.com");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageSalutationField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageSalutationField)).sendKeys("newPathologistSalutation");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageStateLicenceField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageStateLicenceField)).sendKeys("newPathologistStateLicence");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageMedicareNumberField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageMedicareNumberField)).sendKeys("0000000000");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageMedicaidNumberField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageMedicaidNumberField)).sendKeys("0000000000");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageUPINNumberField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageUPINNumberField)).sendKeys("000000000");

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageNPIField)).clear();
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageNPIField)).sendKeys("000000000");
            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageUPINNumberField)).click();

            driver.findElement(By.cssSelector(WElement.pathologistUpdatePageAddSubmit)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Lab pathologist should be updated successfully.$")
    public void isPathologistWasUpdateSuccessfully() {
        try {
            Thread.sleep(1000);
            String statusOperation = driver.findElement(By.cssSelector(WElement.updatePathologistSuccessTitle)).getText();
            assertTrue(statusOperation.equals("Success"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        driver.close();
    }
}
