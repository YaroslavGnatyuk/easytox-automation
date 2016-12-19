package com.easytox.automation.steps.security.search.cgilabadmin.ET_002_Search_Results;

import com.easytox.automation.driver.DriverBase;
import com.easytox.automation.steps.security.search.cgilabadmin.Case;
import com.easytox.automation.steps.security.search.cgilabadmin.ET_001_Case_Search.CaseSearchSteps;
import com.easytox.automation.steps.security.search.cgilabadmin.TestData;
import com.easytox.automation.steps.security.search.cgilabadmin.WElement;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchResultSteps {
    private WebDriver driver;
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String casePage = "http://bmtechsol.com:8080/easytox/caseOrder/list";
    private List<Case> testData;

    private Logger log = Logger.getLogger(CaseSearchSteps.class);

    @Before
    public void init() {
        try {
            DriverBase.instantiateDriverObject();
            driver = DriverBase.getDriver();
            driver.navigate().to(easyToxAddress);
            driver.manage().window().maximize();

            testData = TestData.getCases();

            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("^User login with the physician \"([^\"]*)\" and password \"([^\"]*)\"$$")
    public void loginToEasytox(String usr, String psw) {
        try {
            driver.findElement(By.name("j_username")).sendKeys(usr);
            driver.findElement(By.name("j_password")).sendKeys(psw);
            driver.findElement(By.cssSelector("button.btn.btn-md.btn-primary")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Enter any search criteria and click Search$")
    public void searchByAnyCriteria() {
        try {
            Thread.sleep(1000);
            clearAllFields();

            driver.findElement(By.cssSelector(WElement.caseNumberFieldSearch))
                    .sendKeys(testData.get(0).getCaseNumber());

            driver.findElement(By.cssSelector(WElement.searchButton)).click();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Cases matching entered search criteria are displayed$")
    public void checkResultOfSearchByAnyCriteria() {
        try {
            Thread.sleep(1500);

            String caseAccessionNumber = driver
                    .findElement(By.cssSelector(WElement.caseNumberRowInMyTable))
                    .getText();

            assertEquals(true, caseAccessionNumber.equals(testData.get(0).getCaseNumber()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter case accession patient, first name, lastname, DOB in the search box$")
    public void searchBySomeParameters() {
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            clearAllFields();

            driver.findElement(By.cssSelector(WElement.firstNameFieldSearch))
                    .sendKeys(testData.get(0).getFirstName());

            driver.findElement(By.cssSelector(WElement.lastNameFieldSearch))
                    .sendKeys(testData.get(0).getLastName());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String dob = testData.get(0).getDob().format(formatter);

            driver.findElement(By.cssSelector(WElement.dobFieldSearch))
                    .sendKeys(dob);
            Thread.sleep(300);

            driver.findElement(By.cssSelector(/*"#dateOfBirth > input:nth-child(1)"*/WElement.dobFieldSearch))
                    .sendKeys(Keys.ESCAPE);
            Thread.sleep(300);

            driver.findElement(By.cssSelector(WElement.caseNumberFieldSearch))
                    .sendKeys(testData.get(0).getCaseNumber());

            driver.findElement(By.cssSelector(WElement.searchButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Matching records with entered data should be populated in the case list$")
    public void resultOfSearchBySomeParameters() {
        try {
            Thread.sleep(1000);
            String caseAccessionNumber = driver
                    .findElement(By.cssSelector(WElement.caseNumberRowInMyTable))
                    .getText();

            assertEquals(true, caseAccessionNumber.equals(testData.get(0).getCaseNumber()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click Case Accession number$")
    public void clickOnTheCaseAccessionNumber() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.caseNumberRowInMyTable)).click(); //I click on the case number
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Case screen should be displayed for editing$")
    public void checkScreen() {
        try {
            Thread.sleep(3000);
            String currentPage = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText(); // I read name of the page from header-title
            log.info(currentPage);
            assertEquals(true, currentPage.equals("Update Case"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Verify that for a case with Status as Ready for Pathologist Processing, a delete icon is displayed against the case. Click delete icon.$")
    public void clickDeleteIcon() {
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            clearAllFields();

            new Select(driver.findElement(By.cssSelector(WElement.statusFieldSearch))).selectByVisibleText("InProcess");
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButton)).click();
            Thread.sleep(1500);

            boolean isDeleteIconShowed = driver.findElement(By.cssSelector(WElement.deleteIconInMyTable)).isDisplayed();

            assertTrue(isDeleteIconShowed);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Case gets deleted after receiving confirmation from the user.$")
    public void checkConfirmationWindow() {
        try {
            String caseAccessionNumber = driver.findElement(By.cssSelector(WElement.caseNumberRowInMyTable)).getText();

            driver.findElement(By.cssSelector(WElement.filterField)).sendKeys(caseAccessionNumber);
            Thread.sleep(100);
            String statusBefore = driver.findElement(By.cssSelector(WElement.statusOfCaseMainTable)).getText();

            driver.findElement(By.cssSelector(WElement.deleteIconInMyTable)).click();
            Thread.sleep(100);
            driver.findElement(By.cssSelector(WElement.confirmationDeletingInMyTable)).click();
            driver.findElement(By.cssSelector(WElement.caseNumberFieldSearch)).sendKeys(caseAccessionNumber);
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.searchButton)).click();
            Thread.sleep(2000);

            driver.findElement(By.cssSelector(WElement.filterField)).sendKeys(caseAccessionNumber);
            Thread.sleep(100);
            String statusAfter = driver.findElement(By.cssSelector(WElement.statusOfCaseMainTable)).getText();

            log.info("status before ->" + statusBefore + ", status after -> " + statusAfter);
            assertEquals(true, statusBefore.equals("processing") && statusAfter.equals("finalized"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Verify that for a case with Status as Finalized, a edit icon is displayed against the case. Click edit icon.$")
    public void checkIconEditForCaseWithStatusFinalize() {
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            clearAllFields();

            new Select(driver.findElement(By.cssSelector(WElement.statusFieldSearch))).selectByVisibleText("Completed");
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButton)).click();
            Thread.sleep(1000);

            boolean isDeleteIconShowed = driver.findElement(By.cssSelector(WElement.editIconInMyTable)).isDisplayed();

            assertTrue(isDeleteIconShowed);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Two radio options are displayed Case Correct and Case Revise$")
    public void checkResultAfterClick() {
        try {
            driver.findElement(By.cssSelector(WElement.editIconInMyTable)).click();
            Thread.sleep(1000);
            boolean caseCorrectIsDisplayed = driver.
                    findElement(By.cssSelector(WElement.modalWindowCorrectCase1)).isDisplayed();
            boolean caseReviseIsDisplayed = driver.
                    findElement(By.cssSelector(WElement.modalWindowReviseCase1)).isDisplayed();
            assertEquals(true, caseCorrectIsDisplayed && caseReviseIsDisplayed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Select Case Correct option.$")
    public void selectCaseCorrectOption() {
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            clearAllFields();

            new Select(driver.findElement(By.cssSelector(WElement.statusFieldSearch))).selectByVisibleText("Completed");
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButton)).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.editIconInMyTable)).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.modalWindowCorrectCase1)).click();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Case screen should be displayed for editing. Correct option.$")
    public void checkDisplayForEditingAfterChoseCorrect() {
        try {
            Thread.sleep(1000);

            String currentPage = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText(); // I read name of the page from header-title
            log.info(currentPage);
            assertEquals(true, currentPage.equals("Correct Case"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Select Case Revise option.$")
    public void selectCaseReviseOption() {
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            clearAllFields();

            new Select(driver.findElement(By.cssSelector(WElement.statusFieldSearch))).selectByVisibleText("Completed");
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButton)).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.editIconInMyTable)).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.modalWindowReviseCase1)).click();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Case screen should be displayed for editing. Revise option.$")
    public void checkDisplayForEditingAfterChooseRevise() {
        try {
            Thread.sleep(1000);
            String currentPage = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText(); // I read name of the page from header-title
            log.info(currentPage);
            assertEquals(true, currentPage.equals("Revise Case"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Verify the Report column for finalized cases.$")
    public void chooseFinalizedStatus(){
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            clearAllFields();
            new Select(driver.findElement(By.cssSelector(WElement.statusFieldSearch))).selectByVisibleText("Completed");
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Then("^A PDF icon for viewing the report should be displayed. Upon clicking the icon, case report should be displayed.$")
    public void isPDFIconShowed(){
        try {
            Thread.sleep(1000);
            boolean isPDFIconShowed = driver.findElement(By.cssSelector(WElement.saveToPDFButton1)).isDisplayed();

            assertTrue(isPDFIconShowed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        driver.close();
    }

    private void clearAllFields() { // I clear all field for input search parameters
        driver.findElement(By.cssSelector(WElement.firstNameFieldSearch)).clear();
        driver.findElement(By.cssSelector(WElement.lastNameFieldSearch)).clear();
        driver.findElement(By.cssSelector(WElement.dobFieldSearch)).clear();
        driver.findElement(By.cssSelector(WElement.dobFieldSearch)).sendKeys(Keys.ESCAPE);
        new Select(driver.findElement(By.cssSelector(WElement.statusFieldSearch))).selectByVisibleText("Select Status");
        new Select(driver.findElement(By.cssSelector(WElement.physicianFieldSearch))).selectByVisibleText("Select Physician");
        driver.findElement(By.cssSelector(WElement.caseNumberFieldSearch)).clear();
        driver.findElement(By.cssSelector(WElement.medicalRecordNumberFieldSearch)).clear();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
