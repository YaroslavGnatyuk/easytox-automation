package com.easytox.automation.steps.security.reports.case_1_reports;

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
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CreateNewOrderSteps {
    private WebDriver driver;
    private Logger log = Logger.getLogger(CreateNewOrderSteps.class);
    private static final String LOGIN_URL = "http://bmtechsol.com:8080/easytox/";
    private static final String ERROR_WHEN_LOGIN_URL = "http://bmtechsol.com:8080/easytox/?login_error=1&format=";
    private static final String CREATE_NEW_ORDER_URL = "http://bmtechsol.com:8080/easytox/orderFrom/create";

    private int totalRecordsInOrderListBeforeCreationNewOrder = 0;
    private int totalRecordsInOrderListAfterCreationNewOrder = 0;

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @When("^Login to Easytox with \"([^\"]*)\" and \"([^\"]*)\" credentials.$")
    public void loginToEasyTox(String username, String password) {
        driver.navigate().to(LOGIN_URL);
        driver.findElement(By.name(WElement.LOGIN_PAGE_FIELD_NAME)).sendKeys(username);
        driver.findElement(By.name(WElement.LOGIN_PAGE_PASSWORD_FIELD)).sendKeys(password);
        driver.findElement(By.cssSelector(WElement.LOGIN_PAGE_LOGIN_BUTTON)).click();
    }

    @Then("^User login should be successful.$")
    public void checkCurrentPage() {
        try {
            String currentUrl = driver.getCurrentUrl();
            assertTrue(!currentUrl.equals(LOGIN_URL) && !currentUrl.equals(ERROR_WHEN_LOGIN_URL));
            totalRecordsInOrderListBeforeCreationNewOrder = driver
                    .findElements(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                    .size();

            log.info(totalRecordsInOrderListBeforeCreationNewOrder);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Select 'Create Order' from left hand menu\\.$")
    public void clickOnCreateOrder() {
        driver.findElement(By.cssSelector(WElement.CREATE_NEW_ORDER)).click();
    }

    @Then("^'Create Order' page should be displayed\\.$")
    public void create_Order_page_should_be_displayed() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.equals(CREATE_NEW_ORDER_URL));
    }

    @When("^Verify the value displayed in 'Location' drop down\\.$")
    public void checkValueInLocationDropDown() {
        List<WebElement> locations =
                new Select(driver.findElement(By.cssSelector(WElement.ORDER_LOCATION)))
                        .getOptions();
        assertFalse(locations.isEmpty());
    }

    @Then("^\"([^\"]*)\" should be displayed in the Location drop down\\.$")
    public void checkLabClientInDropDownMenu(String labClient) {
        List<WebElement> locations =
                new Select(driver.findElement(By.cssSelector(WElement.ORDER_LOCATION)))
                        .getOptions();
        List<String> itemsLocation = locations.stream().map(WebElement::getText).collect(Collectors.toList());

        assertTrue(itemsLocation.contains(labClient));
    }

    @When("^Select \"([^\"]*)\" from the location$")
    public void selectDesireLabClient(String labClient) {
        new Select(driver.findElement(By.cssSelector(WElement.ORDER_LOCATION)))
                .selectByVisibleText(labClient);
    }

    @Then("^User should be able to select the desired value \"([^\"]*)\" successfully.$")
    public void checkResultOfSelectionLabClient(String labClient) {

        int firstAndOnlyOneElement = 0;
        List<WebElement> allSelectedLab =
                new Select(driver.findElement(By.cssSelector(WElement.ORDER_LOCATION)))
                        .getAllSelectedOptions();
        String selectedLab = allSelectedLab.get(firstAndOnlyOneElement).getText();

        assertTrue(allSelectedLab.size() == 1 && selectedLab.equals(labClient));
    }

    @When("^Select Patient \"([^\"]*)\" from the 'Patient' drop down.$") //TODO: 1/19/17
    public void selectPatient(String patientForSelect) {
        List<WebElement> allPatientFromDropDown = new Select(driver.findElement(By.cssSelector(WElement.ORDER_PATIENT))).getOptions();

        int indexOfSelectedPatient = getIndexOfOptionForSelect(allPatientFromDropDown, patientForSelect);

        if (indexOfSelectedPatient != -1) {
            new Select(driver.findElement(By.cssSelector(WElement.ORDER_PATIENT))).selectByIndex(indexOfSelectedPatient);
        } else {
            log.info("Patient wasn't found!");
        }

        assertTrue(indexOfSelectedPatient != -1);
    }

    @Then("^Patient \"([^\"]*)\" should be selected successfully.$")
    public void checkResultOfSelectionPatient(String patient) {
        int indexOfPatientsName = 0;
        String selectedPatient = new Select(driver.findElement(By.cssSelector(WElement.ORDER_PATIENT)))
                .getFirstSelectedOption()
                .getText().split(",")[indexOfPatientsName].trim();

        assertTrue(selectedPatient.equals(patient));
    }

    @When("^Enter a valid Date Collected.$")
    public void enterValidDate() {
        driver.findElement(By.cssSelector(WElement.ORDER_DATE)).click();
    }

    @Then("^Default Date should be today's date. User should be able to select any other date.$")
    public void checkDefaultDate() {
        boolean datePickerIsShowed = driver.findElement(By.xpath(WElement.ORDER_DATE_PICKER)).isDisplayed();

        String defaultDate = driver.findElement(By.cssSelector(WElement.ACTIVE_DATE)).getText();
        String defaultMonthAndYear = driver.findElement(By.xpath(WElement.MONTH_AND_YEAR)).getText();

        LocalDate currentDate = LocalDate.now();
        String currentMonth = currentDate.getMonth().name();
        int currentYear = currentDate.getYear();
        int currentDay = currentDate.getDayOfMonth();

        int indexOfMonth = 0;
        int indexOfYear = 1;

        assertTrue(datePickerIsShowed);
        assertTrue(currentDay == Integer.valueOf(defaultDate));
        assertTrue(currentMonth.equalsIgnoreCase(defaultMonthAndYear.split(" ")[indexOfMonth]));
        assertTrue(currentYear == Integer.valueOf(defaultMonthAndYear.split(" ")[indexOfYear]));
    }

    @When("^Select a Case Type$")
    public void selectCaseType() {
        String caseUrine = "Urine";
        new Select(driver.findElement(By.cssSelector(WElement.ORDER_CASE))).selectByVisibleText(caseUrine);
    }

    @Then("^Case Type should be selected successfully.$")
    public void checkResultOfCaseSelection() {
        String selectedCase = new Select(driver.findElement(By.cssSelector(WElement.ORDER_CASE)))
                .getFirstSelectedOption()
                .getText();

        assertNotNull(selectedCase);
    }

    @When("^Verify value in Loggedin By field.$")
    public void verifyValueInLoggedInField() {
        boolean isLoggedInFieldShowed = driver.findElement(By.cssSelector(WElement.ORDER_LOGGED_IN)).isDisplayed();
        assertTrue(isLoggedInFieldShowed);
    }

    @Then("^Value in Loggedin By should be populated with \"([^\"]*)\".$")
    public void checkValueInLoggedInField(String loggedInValue) {
        String loggedInValueWeHave = driver.findElement(By.cssSelector(WElement.ORDER_LOGGED_IN)).getAttribute("value");
        assertTrue(loggedInValueWeHave.equals(loggedInValue));
    }

    @When("^Select Prescribed Medicine as \"([^\"]*)\"$")
    public void selectSubscribeMedicine(String medicine) {
        new Select(driver.findElement(By.cssSelector(WElement.ORDER_PRESCRIBE_MEDICINE))).selectByVisibleText(medicine);
    }

    @Then("^User selection should be successful.$")
    public void checkTheMedicineSelection() {
        String medicineWeHave = new Select(driver.findElement(By.cssSelector(WElement.ORDER_PRESCRIBE_MEDICINE)))
                .getFirstSelectedOption()
                .getText();
        String medicineShouldBe = "Drug1";

        assertTrue(medicineWeHave.equals(medicineShouldBe));
    }

    @When("^Select primary Physician as \"([^\"]*)\".$")
    public void selectPrimaryPhysician(String physician) {
        boolean isPrimaryPhysicianShowed = driver.findElement(By.cssSelector(WElement.ORDER_PRIMARY_PHYSICIAN))
                .isDisplayed();
        assertTrue(isPrimaryPhysicianShowed);
    }

    @Then("^User selection of Physician should be \"([^\"]*)\" successful.$")
    public void checkThePhysicianSelection(String physicianShouldBe) {
        String primaryPhysicianWeHave = driver.findElement(By.cssSelector(WElement.ORDER_PRIMARY_PHYSICIAN)).getText();
        assertTrue(primaryPhysicianWeHave.equals(physicianShouldBe));
    }

    @When("Select Pathologist as \"([^\"]*)\"")
    public void selectThePathologist(String pathologist) {
        Optional<WebElement> path = new Select(driver
                .findElement(By.cssSelector(WElement.PATHOLOGIST_GROUP))
                .findElement(By.cssSelector(WElement.ORDER_SELECT_PATHOLOGIST)))
                .getOptions()
                .stream().filter(e -> e.getText().equals(pathologist)).findFirst();

        if (path.isPresent()) {
            path.get().click();
        }
    }

    @Then("^User selection of Pathologist \"([^\"]*)\" should be successful.$")
    public void checkResultPathologistSelection(String pathologistShouldBe) {
        String pathologistWeSelect = new Select(driver
                .findElement(By.cssSelector(WElement.PATHOLOGIST_GROUP))
                .findElement(By.cssSelector(WElement.ORDER_SELECT_PATHOLOGIST)))
                .getFirstSelectedOption().getText();

        assertTrue(pathologistWeSelect.equals(pathologistShouldBe));
    }

    @When("^Select Compound Profile as \"([^\"]*)\"$")
    public void selectCompoundProfile(String compoundProfile) {
        List<WebElement> options = new Select(driver.findElement(By.cssSelector(WElement.ORDER_COMPOUND_PROFILE)))
                .getOptions();
        int indexOfCompoundProfile = getIndexOfOptionForSelect(options, compoundProfile);

        new Select(driver.findElement(By.cssSelector(WElement.ORDER_COMPOUND_PROFILE)))
                .selectByIndex(indexOfCompoundProfile);

    }

    @Then("^User selection of compound profile \"([^\"]*)\" should be successful.$")
    public void checkCompoundProfileSelection(String compoundProfileShouldBe) {
        String compoundProfileWeHave = new Select(driver.findElement(By.cssSelector(WElement.ORDER_COMPOUND_PROFILE)))
                .getFirstSelectedOption().getText();
        assertTrue(compoundProfileWeHave.equals(compoundProfileShouldBe));
    }

    private int getIndexOfOptionForSelect(List<WebElement> allPatients, String selectedPatient) {
        int indexOfThePatientsName = 0;
        for (int i = 0; i < allPatients.size(); i++) {
            String[] allDataOfSelectedPatient = allPatients.get(i).getText().split(",");
            String patientName = allDataOfSelectedPatient[indexOfThePatientsName].trim();

            if (patientName.equals(selectedPatient)) {
                return i;
            }
        }
        return -1;
    }

    @When("^Verify the details displayed when Compound Profile is selected.$")
    public void verifyTheDetails() {
        boolean isCompound1Displayed = driver.findElement(By.cssSelector(WElement.ORDER_COMPOUND1)).isDisplayed();
        boolean isCompound2Displayed = driver.findElement(By.cssSelector(WElement.ORDER_COMPOUND2)).isDisplayed();
        boolean isVCompound1Displayed = driver.findElement(By.cssSelector(WElement.ORDER_VCOMPOUND1)).isDisplayed();
        boolean isVCompound2Displayed = driver.findElement(By.cssSelector(WElement.ORDER_VCOMPOUND2)).isDisplayed();

        assertTrue(isCompound1Displayed);
        assertTrue(isCompound2Displayed);
        assertTrue(isVCompound1Displayed);
        assertTrue(isVCompound2Displayed);
    }

    @Then("^Following details should be populated in Create Order screen:Test Screen - with values \"([^\"]*)\" and \"([^\"]*)\"$")
    public void checkValueInTestScreen(String valueShouldBe1, String valueShouldBe2) {
        String valueWeHave1 = new Select(driver.findElement(By.cssSelector(WElement.ORDER_COMPOUND1)))
                .getFirstSelectedOption()
                .getText();
        String valueWeHave2 = new Select(driver.findElement(By.cssSelector(WElement.ORDER_COMPOUND2)))
                .getFirstSelectedOption()
                .getText();

        assertTrue(valueWeHave1.equals(valueShouldBe1) && valueWeHave2.equals(valueShouldBe2));
    }

    @And("^Validity Testing - with values \"([^\"]*)\" and \"([^\"]*)\"$")
    public void checkValueInValidityTesting(String valueShouldBe1, String valueShouldBe2) {
        String valueWeHave1 = new Select(driver.findElement(By.cssSelector(WElement.ORDER_VCOMPOUND1)))
                .getFirstSelectedOption()
                .getText();
        String valueWeHave2 = new Select(driver.findElement(By.cssSelector(WElement.ORDER_VCOMPOUND2)))
                .getFirstSelectedOption()
                .getText();

        assertTrue(valueWeHave1.equals(valueShouldBe1) && valueWeHave2.equals(valueShouldBe2));
    }

    @When("^Click Submit$")
    public void clickSubmit() {
        driver.findElement(By.cssSelector(WElement.ORDER_SUBMIT)).click();
    }

    @Then("^Order List screen with newly created order should be displayed.$")
    public void checkNewOrder() {
        try {
            Thread.sleep(2000);
            String activePageShouldBe = "Order List";
            String activePageWeHave = driver.findElement(By.cssSelector(WElement.ACTIVE_PAGE)).getText();

            totalRecordsInOrderListAfterCreationNewOrder = driver
                    .findElements(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                    .size();

            Thread.sleep(1000);

            assertTrue(activePageWeHave.equals(activePageShouldBe));
            assertTrue(totalRecordsInOrderListBeforeCreationNewOrder < totalRecordsInOrderListAfterCreationNewOrder);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        driver.close();
    }
}