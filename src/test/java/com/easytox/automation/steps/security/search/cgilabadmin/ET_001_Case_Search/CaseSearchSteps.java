package com.easytox.automation.steps.security.search.cgilabadmin.ET_001_Case_Search;

import com.easytox.automation.driver.DriverBase;
import com.easytox.automation.steps.security.search.cgilabadmin.TestData;
import com.easytox.automation.steps.security.search.cgilabadmin.Case;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CaseSearchSteps {
    private WebDriver driver;
    private static final String easytoxAddress = "http://bmtechsol.com:8080/easytox/";
    private List<Case> testData;

    private Logger log = Logger.getLogger(CaseSearchSteps.class);

    @Before
    public void init() {
        testData = TestData.getCases();

        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.manage().window().maximize();
    }

    @Given("^User login with the physician \"([^\"]*)\" and password \"([^\"]*)\"$$")
    public void loginToEasytox(String usr, String psw) {
        try {
            driver.navigate().to(easytoxAddress);
            Thread.sleep(500);
            driver.findElement(By.name("j_username")).sendKeys(usr);
            driver.findElement(By.name("j_password")).sendKeys(psw);
            driver.findElement(By.cssSelector("button.btn.btn-md.btn-primary")).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter a valid first name in the First Name field and click search$")
    public void searchByFirstName() {
        try {
            Thread.sleep(2000);
            clearAllFields();
            driver.findElement(By.cssSelector(WElement.firstNameFieldSearch))
                    .sendKeys(testData.get(0).getFirstName());
            driver.findElement(By.cssSelector(WElement.searchButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^All the cases with matching entered first name should be displayed in the Search results$")
    public void checkSearchResultByFirstName() {
        try {
            Thread.sleep(1000);

            List<WebElement> fields = driver.findElements(By.cssSelector(WElement.rowInMyTable));  //I got all rows from table
            fields.remove(0);   //I removed first row with descriptions for columns

            for (WebElement element : fields) {
                String patientsFirstName = element.getText().split("\n")[2].trim().split(" ")[0]; // I selected patients name only
                assertEquals(true, patientsFirstName.equalsIgnoreCase(testData.get(0).getFirstName()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter a valid last name in the Last Name field and click search$")
    public void searchByLastName() {
        try {
            Thread.sleep(1000);
            clearAllFields();
            driver.findElement(By.cssSelector(WElement.lastNameFieldSearch))
                    .sendKeys(testData.get(0).getLastName());

            driver.findElement(By.cssSelector(WElement.searchButton)).click();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^All the cases with matching entered last  name should be displayed in the Search results$")
    public void checkSearchResultByLastName() {
        try {
            Thread.sleep(1000);

            List<WebElement> fields = driver.findElements(By.cssSelector(WElement.rowInMyTable));  //I got all rows from table
            fields.remove(0);   //I removed first row with descriptions for columns

            for (WebElement element : fields) {
                String patientsLastName = element.getText().split("\n")[2].trim().split(" ")[1]; // I selected patients name only
                assertEquals(true, patientsLastName.equalsIgnoreCase(testData.get(0).getLastName()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter a valid DOB in the Date of Birth field in MM/DD/YYYY format and click search$")
    public void searchByDOB() {
        try {
            Thread.sleep(1000);

            clearAllFields();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String dob = testData.get(0).getDob().format(formatter);

            driver.findElement(By.cssSelector(WElement.dobFieldSearch))
                    .sendKeys(dob);
            Thread.sleep(300);

            driver.findElement(By.cssSelector(WElement.dobFieldSearch))
                    .sendKeys(Keys.ESCAPE);
            Thread.sleep(300);

            driver.findElement(By.cssSelector(WElement.searchButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^All the cases with matching DOB should be displayed in the Search results$")
    public void checkSearchResultByDOB() {
        try {
            Thread.sleep(1000);

            List<WebElement> fields = driver.findElements(By.cssSelector(WElement.rowInMyTable));  //I got all rows from table
            fields.remove(0);   //I removed first row with descriptions for columns

            for (int i = 0; i < fields.size(); i++) {
                String patientsDescription = fields.get(i)
                        .findElement(By.cssSelector("#myTable > tr:nth-child(" + (i + 2) + ") > td:nth-child(1) > a:nth-child(1)"))
                        .getAttribute("title"); //This selector point on description of field Case acc#.
                // (i+2) - this is number of row

                String dobFromView = patientsDescription.split(", ")[1].trim().split(" ")[1];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
                String dobFromTestData = testData.get(0).getDob().format(formatter);

                assertEquals(true, dobFromView.equals(dobFromTestData));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Select a value from the Status drop down and click search$")
    public void searchByStatus() {
        try {
            Thread.sleep(1000);
            clearAllFields();
            new Select(driver.findElement(By.cssSelector(WElement.statusFieldSearch)))
                    .selectByVisibleText(testData.get(0).getStatus());

            driver.findElement(By.cssSelector(WElement.searchButton)).click();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^All the cases in selected status should be displayed in the Search results")
    public void checkResultSearchByStatus() {
        try {
            Thread.sleep(1000);

            List<WebElement> fields = driver.findElements(By.cssSelector(WElement.rowInMyTable));  //I got all rows from table
            fields.remove(0);   //I removed first row with descriptions for columns

            for (int i = 0; i < fields.size(); i++) {
                String ca = fields.get(i)
                        .findElement(By.cssSelector("#myTable > tr:nth-child(" + (i + 2) + ") > td:nth-child(1) > a:nth-child(1)"))
                        .getText();

                driver.findElement(By.cssSelector("#caseorder_filter > label:nth-child(1) > input:nth-child(1)")).sendKeys(ca);

                String fieldStatus = driver.findElement(By.cssSelector(".odd > td:nth-child(7)")).getText();

                assertEquals(true, fieldStatus.equals("finalized"));
                driver.findElement(By.cssSelector("#caseorder_filter > label:nth-child(1) > input:nth-child(1)")).clear();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Select a value from the Physician drop down and click search$")
    public void searchByPhysician() {
        try {
            Thread.sleep(1000);
            clearAllFields();

            new Select(driver.findElement(By.cssSelector(WElement.physicianFieldSearch)))
                    .selectByVisibleText(testData.get(0).getPhysician());

            driver.findElement(By.cssSelector(WElement.searchButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^All the cases related to selected physician should be displayed in the Search results$")
    public void checkResultSearchByPhysician() {
        try {
            Thread.sleep(1000);

            List<WebElement> fields = driver.findElements(By.cssSelector(WElement.rowInMyTable));  //I got all rows from table
            fields.remove(0);   //I removed first row with descriptions for columns

            for (int i = 0; i < fields.size(); i++) {
                String patientsDescription = fields.get(i)
                        .findElement(By.cssSelector("#myTable > tr:nth-child(" + (i + 2) + ") > td:nth-child(1) > a:nth-child(1)"))
                        .getAttribute("title"); //This selector point on description of field case acc.
                // (i+2) - this is number of row

                String physicianFromView = patientsDescription.split(", ")[3].trim().split(" ")[1] +
                        " " + patientsDescription.split(", ")[3].trim().split(" ")[2];
                String physicianFromTestData = testData.get(0).getPhysician();

                assertEquals(true, physicianFromView.equals(physicianFromTestData));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter a valid case number and click search$")
    public void searchByCaseAccessionNumber() {
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

    @Then("^Matching case should be displayed in the  Search results$")
    public void checkResultOfSearchingByCaseAccessionNumber() {
        try {
            Thread.sleep(1000);

            String caseAccessionNumber = driver
                    .findElement(By.cssSelector("#myTable > tr:nth-child(" + 2 + ") > td:nth-child(1) > a:nth-child(1)"))
                    .getText();

            assertEquals(true, caseAccessionNumber.equals(testData.get(0).getCaseNumber()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter a valid Medical Record Number and click search$")
    public void searchByMedicalRecordNumber() {
        try {
            Thread.sleep(1000);
            clearAllFields();

            driver.findElement(By.cssSelector(WElement.medicalRecordNumberFieldSearch))
                    .sendKeys(testData.get(0).getMedNumber());
            driver.findElement(By.id("searchbtn")).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Then("^Matching case should be displayed in the Search results$")
    public void checkResultOfSearchingByMedicalRecordNumber() {
        try {
            Thread.sleep(1000);

            List<WebElement> fields = driver.findElements(By.cssSelector(WElement.rowInMyTable));  //I got all rows from table
            fields.remove(0);   //I removed first row with descriptions for columns

            for (int i = 0; i < fields.size(); i++) {
                String patientsDescription = fields.get(i)
                        .findElement(By.cssSelector("#myTable > tr:nth-child(" + (i + 2) + ") > td:nth-child(1) > a:nth-child(1)"))
                        .getAttribute("title"); //This selector point on description of field case acc.
                // (i+2) - this is number of row

                String medNumberFromView = patientsDescription.split(", ")[2].trim().split(" ")[2];
                String medNumberFromTestData = testData.get(0).getMedNumber();

                assertEquals(true, medNumberFromView.equals(medNumberFromTestData));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter values in multiple fields and click Search$")
    public void searchByMultipleFields(){
        try {
            Thread.sleep(1000);
            clearAllFields();

            driver.findElement(By.cssSelector(WElement.firstNameFieldSearch))
                    .sendKeys(testData.get(0).getFirstName());

            driver.findElement(By.cssSelector(WElement.lastNameFieldSearch))
                    .sendKeys(testData.get(0).getLastName());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
            String dobFromTestData = testData.get(0).getDob().format(formatter);
            driver.findElement(By.cssSelector(WElement.dobFieldSearch))
                    .sendKeys(dobFromTestData);
            driver.findElement(By.cssSelector(WElement.dobFieldSearch))
                    .sendKeys(Keys.ESCAPE);

            new Select(driver.findElement(By.cssSelector(WElement.statusFieldSearch)))
                    .selectByVisibleText(testData.get(0).getStatus());

            new Select(driver.findElement(By.cssSelector(WElement.physicianFieldSearch)))
                    .selectByVisibleText(testData.get(0).getPhysician());

            driver.findElement(By.cssSelector(WElement.caseNumberFieldSearch))
                    .sendKeys(testData.get(0).getCaseNumber());

            driver.findElement(By.cssSelector(WElement.medicalRecordNumberFieldSearch))
                    .sendKeys(testData.get(0).getMedNumber());

            driver.findElement(By.cssSelector(WElement.searchButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Then("^Cases matching all the entered conditions should be displayed in the Search results$")
    public void resultOfMultipleFieldsSearch(){
        try {
            Thread.sleep(1000);

            String caseAccessionNumber = driver
                    .findElement(By.cssSelector("#myTable > tr:nth-child(" + 2 + ") > td:nth-child(1) > a:nth-child(1)"))
                    .getText();

            assertEquals(true, caseAccessionNumber.equals(testData.get(0).getCaseNumber()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click Search without entering values in any field$")
    public void searchWithoutEnteringData(){
        try {
            Thread.sleep(1000);
            clearAllFields();
            driver.findElement(By.cssSelector(WElement.searchButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Then("^All the cases should be returned$")
    public void resultOfSearchWithoutEnteringData(){
        try {
            Thread.sleep(1000);
            List<WebElement> fieldsFromLeftSideTable = driver.findElements(By.cssSelector(WElement.rowInMyTable));  //I got all rows from table
            fieldsFromLeftSideTable.remove(0);   //I removed first row with descriptions for columns

            driver.navigate().refresh();
            Thread.sleep(300);
            new Select(driver.findElement(By.cssSelector(WElement.quantityOfCasesPerOnePage))).selectByVisibleText("All");
            Thread.sleep(300);
            List<WebElement> elementsOdd = driver.findElements(By.cssSelector("tr.odd"));
            List<WebElement> elementsEven = driver.findElements(By.cssSelector("tr.even"));
            int fieldsFromRightSideTable = elementsOdd.size() + elementsEven.size();

            assertEquals(true, fieldsFromLeftSideTable.size() == fieldsFromRightSideTable);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clearAllFields() {
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

    @After
    public void close() {
        driver.close();
    }
}