package com.easytox.automation.steps.security.search.cgilabadmin.ET_003_Detail_Search;

import com.easytox.automation.driver.DriverBase;
import com.easytox.automation.steps.security.search.cgilabadmin.WElement;
import com.easytox.automation.steps.security.search.cgilabadmin.Case;
import com.easytox.automation.steps.security.search.cgilabadmin.TestData;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by yroslav on 12/10/16.
 */
public class SearchDetailSteps {
    private WebDriver driver;

    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String casePage = "http://bmtechsol.com:8080/easytox/caseOrder/list";
    private static final String createNewCasePage = "http://bmtechsol.com:8080/easytox/caseOrder/create";
    private static final String detailList = "http://bmtechsol.com:8080/easytox/caseOrder/detaillist";

    private Logger log = Logger.getLogger(this.getClass());
    private List<Case> testData;

    @Before
    public void init() {
        try {
            testData = TestData.getCases();

            DriverBase.instantiateDriverObject();
            driver = DriverBase.getDriver();
            driver.manage().window().maximize();
            driver.navigate().to(easyToxAddress);

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

    @When("^Click Detail Search button from the Find Case section.$")
    public void clickOnDetailSearchButton(){
        try {
            Thread.sleep(2000);
            driver.findElement(By.cssSelector(WElement.detailSearchButton)).click(); // This's detail search button
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Detail Search screen with additional search options along with all cases is displayed.$")
    public void isOpenDetailSearchScreen(){
        try {
            Thread.sleep(1000);
            String currentPage = driver.getCurrentUrl();

            assertEquals(true, currentPage.equals(detailList));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @When("^Enter any search criteria and click Search.$")
    public void inputAnyCriteria(){
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.detailSearchButton)).click();// This's detail search button
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.lastNameFieldDetailSearch)).sendKeys(testData.get(0).getLastName()); //This's field for input last name
            driver.findElement(By.cssSelector(WElement.searchButtonOnDetailSearchScreen)).click(); //Click button search
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Then("^Cases matching entered search criteria are displayed.$")
    public void checkResultOfSearchByAnyCriteria(){
        try {
            Thread.sleep(1000);
            List<WebElement> elementsOdd = driver.findElements(By.cssSelector("tr.odd"));   //I get all row from table with selector 'odd'
            List<WebElement> elementsEven = driver.findElements(By.cssSelector("tr.even")); //I get all row from table with selector 'even'

            for (int i = 0; i < elementsOdd.size(); i++) {
                assertEquals(true,elementsOdd.get(i).findElement(By.cssSelector(WElement.lastNameRow)) //Check last name matching
                        .getText().equals(testData.get(0).getLastName())); //I check every results row(selector 'odd') and compare with search parameters
            }

            for (int i = 0; i < elementsEven.size(); i++) {
                assertEquals(true,elementsEven.get(i).findElement(By.cssSelector(WElement.lastNameRow))
                        .getText().equals(testData.get(0).getLastName())); //I check every results row(selector 'even') and compare with search parameters
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @When("^Enter case accesion  patient first name and lastname and DOB in the search box.$")
    public void inputSearchCriteria(){
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.detailSearchButton)).click();// This's detail search button
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.lastNameFieldDetailSearch)).sendKeys(testData.get(0).getLastName()); //This's field for input last name
            driver.findElement(By.cssSelector(WElement.firstNameFieldDetailSearch)).sendKeys(testData.get(0).getFirstName()); //This's field for input first name

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String dob = testData.get(0).getDob().format(formatter);
            driver.findElement(By.cssSelector(WElement.dobFieldDetailSearch)) //This's field for input DOB
                    .sendKeys(dob);
            driver.findElement(By.cssSelector(WElement.dobFieldDetailSearch)) //I click 'escape' to close calendar
                    .sendKeys(Keys.ESCAPE);
            Thread.sleep(300);

            driver.findElement(By.cssSelector(WElement.searchButtonOnDetailSearchScreen)).click(); //Click button search
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Matching records with entered data should be populated in the case list.$")
    public void checkResultOfSearch(){
        try {
            Thread.sleep(1000);
            List<WebElement> elementsOdd = driver.findElements(By.cssSelector("tr.odd")); //I get all row from table with selector 'odd'
            List<WebElement> elementsEven = driver.findElements(By.cssSelector("tr.even")); //I get all row from table with selector 'even'

            for (int i = 0; i < elementsOdd.size(); i++) {
                assertEquals(true,elementsOdd.get(i).findElement(By.cssSelector(WElement.lastNameRow))
                        .getText().equals(testData.get(0).getLastName())); //I compare last name from result with last name from search parameters
                assertEquals(true,elementsOdd.get(i).findElement(By.cssSelector(WElement.firstNameRow))
                        .getText().equals(testData.get(0).getFirstName()));//I compare first name from result with first name from search parameters

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
                String dob = testData.get(0).getDob().format(formatter);
                assertEquals(true,elementsOdd.get(i).findElement(By.cssSelector(WElement.dobRow))
                        .getText().equals(dob)); //I compare 'DOB' from result with 'DOB' from search parameters
            }

            for (int i = 0; i < elementsEven.size(); i++) {
                assertEquals(true,elementsEven.get(i).findElement(By.cssSelector(WElement.lastNameRow))
                        .getText().equals(testData.get(0).getLastName()));
                assertEquals(true,elementsEven.get(i).findElement(By.cssSelector(WElement.firstNameRow))
                        .getText().equals(testData.get(0).getFirstName()));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
                String dob = testData.get(0).getDob().format(formatter);
                assertEquals(true,elementsOdd.get(i).findElement(By.cssSelector(WElement.dobRow))
                        .getText().equals(dob));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click Case Accession number.$")
    public void clickCaseAccessionNumber(){
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.caseAccessionNumberRow)).click(); //Click on case accession number
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Case screen should be displayed for editing.$")
    public void checkOpenWindow(){
        try {
            Thread.sleep(2000);
            String currentPage = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText(); // I read name of the page from header-title
            assertEquals(true, currentPage.equals("Update Case"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Verify that for a case with Status as Ready for Pathologist Processing, a delete icon is displayed against the case. Click delete icon.$")
    public void clickDeleteIcon() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.detailSearchButton)).click();// This's detail search button
            Thread.sleep(1000);
            new Select(driver.findElement(By.cssSelector(WElement.statusFieldDetailSearch))).selectByVisibleText("Processing");
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButtonOnDetailSearchScreen)).click();  // This's search button
            Thread.sleep(1000);

            boolean isDeleteButtonShowed = driver.findElement(By.cssSelector(WElement.deleteButton)).isDisplayed();
            assertEquals(true,isDeleteButtonShowed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Case gets deleted after receiving confirmation from the user.$")
    public void checkConfirmationWindow(){
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.deleteButton)).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(WElement.confirmationDeleting)).click();
            Thread.sleep(1000);
            String resultDeleting = driver.findElement(By.cssSelector(WElement.resultStringOfDeleting)).getText();
            assertEquals(true, resultDeleting.equals("Success"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Verify that for a case with Status as Finalized, a edit icon is displayed against the case. Click edit icon.$")
    public void checkAndClickEditIconForCaseWithStatusFinalized() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.detailSearchButton)).click();// This's detail search button
            Thread.sleep(1000);
            new Select(driver.findElement(By.cssSelector(WElement.statusFieldDetailSearch))).selectByVisibleText("finalized"); //Choose "finalized" in select drop box "status"
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButtonOnDetailSearchScreen)).click();  // This's search button
            Thread.sleep(1000);

            boolean isEditButtonShowed = driver.findElement(By.cssSelector(WElement.correctionButton)).isDisplayed(); //Check edit icon on view
            assertTrue(isEditButtonShowed);

            driver.findElement(By.cssSelector(WElement.correctionButton)).click(); //Click on edit icon
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Two radio options are displayed:Case Correct and Case Revise.$")
    public void checkModalWindowWithTwoRadioOptions() {
        boolean isCaseCorrectOptionShowed = driver
                .findElement(By.cssSelector(WElement.modalWindowCorrectCase))
                .isDisplayed();
        boolean isCaseReviseOptionShowed = driver
                .findElement(By.cssSelector(WElement.modalWindowReviseCase))
                .isDisplayed();

        assertTrue(isCaseCorrectOptionShowed && isCaseReviseOptionShowed);

        driver.findElement(By.cssSelector(WElement.closeEditModalWindowButton)).click();
    }

    @When("^Select Case Correct option.$")
    public void selectCaseCorrectOption() {
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.detailSearchButton)).click();// This's detail search button
            Thread.sleep(1000);
            new Select(driver.findElement(By.cssSelector(WElement.statusFieldDetailSearch))).selectByVisibleText("finalized"); //Choose "finalized" in select drop box "status"
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButtonOnDetailSearchScreen)).click();  // This's search button
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.correctionButton)).click(); //Click on edit icon
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.modalWindowCorrectCase)).click(); //click on correct option of modal window

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
            assertTrue(currentPage.equals("Correct Case"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Select Case Revise option.$")
    public void selectCaseReviseOption() {
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.detailSearchButton)).click();// This's detail search button
            Thread.sleep(1000);
            new Select(driver.findElement(By.cssSelector(WElement.statusFieldDetailSearch))).selectByVisibleText("finalized"); //Choose "finalized" in select drop box "status"
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButtonOnDetailSearchScreen)).click();  // This's search button
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.correctionButton)).click(); //Click on edit icon
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.modalWindowReviseCase)).click(); //click on revise option of modal window

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Case screen should be displayed for editing. Revise option.$")
    public void checkDisplayForEditingAfterChoseRevise() {
        try {
            Thread.sleep(1000);

            String currentPage = driver.findElement(By.cssSelector(WElement.pageHeaderTitle)).getText(); // I read name of the page from header-title
            log.info(currentPage);
            assertTrue(currentPage.equals("Revise Case"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click Create New Case icon.$")
    public void clickCreateNewCase(){
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.createNewCaseButton)).click();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Then("^User navigates to Create New Case screen.$")
    public void isCreateNewCaseDisplayShowed(){
        try {
            Thread.sleep(1000);
            assertTrue(driver.getCurrentUrl().equals(createNewCasePage));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Verify the Report column for finalized cases.$")
    public void findAllFinalizedCases(){
        try {
            driver.navigate().to(casePage);
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.detailSearchButton)).click();// This's detail search button
            Thread.sleep(1000);
            new Select(driver.findElement(By.cssSelector(WElement.statusFieldDetailSearch))).selectByVisibleText("finalized"); //Choose "finalized" in select drop box "status"
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.searchButtonOnDetailSearchScreen)).click();  // This's search button
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Then("^A PDF icon for viewing the report should be displayed. Upon clicking the icon, case report should be displayed.$")
    public void isPDFIconShowed(){
        try {
            Thread.sleep(1000);
            List<WebElement> elementsOdd = driver.findElements(By.cssSelector("tr.odd")); //I get all row from table with selector 'odd'
            List<WebElement> elementsEven = driver.findElements(By.cssSelector("tr.even")); //I get all row from table with selector 'even'

            for (int i = 0; i < elementsOdd.size(); i++) {
                boolean isSaveToPDFButtonShowed = elementsOdd.get(i).findElement(By.cssSelector(WElement.saveToPDFButton)).isDisplayed();
                assertFalse(isSaveToPDFButtonShowed);
            }
            for (int i = 0; i < elementsEven.size(); i++) {
                boolean isSaveToPDFButtonShowed = elementsEven.get(i).findElement(By.cssSelector(WElement.saveToPDFButton)).isDisplayed();
                assertFalse(isSaveToPDFButtonShowed);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        driver.close();
    }
}
