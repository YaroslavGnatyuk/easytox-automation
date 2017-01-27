package com.easytox.automation.steps.security.reports.case_5_reports;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CreateNewOrderSteps5 {
    private WebDriver driver;
    private WebDriverWait wait;
    private Logger log = Logger.getLogger(CreateNewOrderSteps5.class);
    private static final String LOGIN_URL = "http://bmtechsol.com:8080/easytox/";
    private static final String ERROR_IN_LOGIN_URL = "http://bmtechsol.com:8080/easytox/?login_error=1&format=";
    private static final String CREATE_NEW_ORDER_URL = "http://bmtechsol.com:8080/easytox/orderFrom/create";

    private int totalRecordsInOrderListBeforeCreationNewOrder = 0;
    private String caseAccession = null;
    private String newOrder = null;

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 15);
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
            assertTrue(!currentUrl.equals(LOGIN_URL) && !currentUrl.equals(ERROR_IN_LOGIN_URL));

            new Select(driver.findElement(By.cssSelector(WElement.AMOUNT_OF_RECORDS_PER_ONE_PAGE)))
                    .selectByVisibleText("All");
            totalRecordsInOrderListBeforeCreationNewOrder = driver
                    .findElements(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                    .size();

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Select 'Create Order' from left hand menu.$")
    public void clickOnCreateOrder() {
        driver.findElement(By.cssSelector(WElement.CREATE_NEW_ORDER)).click();
    }

    @Then("^'Create Order' page should be displayed.$")
    public void create_Order_page_should_be_displayed() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.equals(CREATE_NEW_ORDER_URL));
    }

    @When("^Verify the value displayed in 'Location' drop down.$")
    public void checkValueInLocationDropDown() {
        List<WebElement> locations =
                new Select(driver.findElement(By.cssSelector(WElement.ORDER_LOCATION)))
                        .getOptions();
        assertFalse(locations.isEmpty());
    }

    @Then("^\"([^\"]*)\" should be displayed in the Location drop down.$")
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

    @When("^Select Patient \"([^\"]*)\" from the 'Patient' drop down.$")
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
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @When("^Select Prescribed Medicine as \"([^\"]*)\" and \"([^\"]*)\".$")
    public void selectSubscribeMedicine(String medicine1, String medicine2) {
        new Select(driver.findElement(By.cssSelector(WElement.ORDER_PRESCRIBE_MEDICINE))).selectByVisibleText(medicine1);
        new Select(driver.findElement(By.cssSelector(WElement.ORDER_PRESCRIBE_MEDICINE))).selectByVisibleText(medicine2);
    }

    @Then("^User selection should be successful.$")
    public void checkTheMedicineSelection() {
        List<String> medicinesWeHave = new Select(driver.findElement(By.cssSelector(WElement.ORDER_PRESCRIBE_MEDICINE)))
                .getAllSelectedOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        int amountDrugsShouldBe = 2;
        String medicineShouldBe1 = "Drug1";
        String medicineShouldBe2 = "Drug2";

        assertTrue(medicinesWeHave.size() == amountDrugsShouldBe);
        assertTrue(medicinesWeHave.contains(medicineShouldBe1));
        assertTrue(medicinesWeHave.contains(medicineShouldBe2));
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
        Optional<WebElement> patholog = new Select(driver
                .findElement(By.cssSelector(WElement.PATHOLOGIST_GROUP))
                .findElement(By.cssSelector(WElement.ORDER_SELECT_PATHOLOGIST)))
                .getOptions()
                .stream().filter(e -> e.getText().equals(pathologist)).findFirst();

        if (patholog.isPresent()) {
            patholog.get().click();
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
            int positionOfNewOrdersID = 3;
            String activePageShouldBe = "Order List";
            String activePageWeHave = driver.findElement(By.cssSelector(WElement.ACTIVE_PAGE)).getText();

            new Select(driver.findElement(By.cssSelector(WElement.AMOUNT_OF_RECORDS_PER_ONE_PAGE)))
                    .selectByVisibleText("All");
            Thread.sleep(1000);
            int totalRecordsInOrderListAfterCreationNewOrder = driver
                    .findElements(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                    .size();

            assertTrue(activePageWeHave.equals(activePageShouldBe));
            assertTrue(totalRecordsInOrderListBeforeCreationNewOrder < totalRecordsInOrderListAfterCreationNewOrder);

            newOrder = driver.findElement(By.cssSelector(WElement.PAGE_TITLE_AFTER_CREATING_ORDER))
                    .getText()
                    .split(" ")[positionOfNewOrdersID];
            logout();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(WElement.LOGIN_AREA))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign out"))).click();
    }

    /**
     * Case finalize
     **/

    @When("^Login to EasyTox with credentials \"([^\"]*)\" and \"([^\"]*)\"$")
    public void loginToEasyToxAgain(String username, String password) {
        driver.navigate().to(LOGIN_URL);
        driver.findElement(By.name(WElement.LOGIN_PAGE_FIELD_NAME)).sendKeys(username);
        driver.findElement(By.name(WElement.LOGIN_PAGE_PASSWORD_FIELD)).sendKeys(password);
        driver.findElement(By.cssSelector(WElement.LOGIN_PAGE_LOGIN_BUTTON)).click();
    }

    @Then("^User login should be successful again.$")
    public void checkCurrentPageAgain() {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(!currentUrl.equals(LOGIN_URL) && !currentUrl.equals(ERROR_IN_LOGIN_URL));
    }

    @When("^Click Pending Orders.$")
    public void clickPendingOrders() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.cssSelector(WElement.PENDING_ORDERS)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Orders List screen should be displayed.$")
    public void checkDisplayedScreen() {
        String activePageShouldBe = "Order List";
        String activePageWeHave = driver.findElement(By.cssSelector(WElement.ACTIVE_PAGE)).getText();

        assertTrue(activePageWeHave.equals(activePageShouldBe));
    }

    @When("^Verify that the order created in above case is displayed in the Orders List.$")
    public void verifyNewOrder() {
        driver.findElement(By.cssSelector(WElement.SEARCH_ORDER_FIELD)).sendKeys(newOrder);
    }

    @Then("^Newly created order should be displayed in the list.$")
    public void newOrderShouldBeDisplayed() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int ordersIDIndex = 0;
        String numberWeHave = driver
                .findElements(By.cssSelector(WElement.ONE_COLUMN_IN_ROW))
                .get(ordersIDIndex)
                .getText();
        assertTrue(numberWeHave.equals(newOrder));
    }

    @When("Click on Order Number.")
    public void clickOnOrderNumber() {
        driver
                .findElement(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                .findElement(By.cssSelector(WElement.NEW_ORDER_CELL))
                .submit();
    }

    @Then("^\"([^\"]*)\" screen should be displayed.$")
    public void checkUpdateCaseOrderScreen(String screenShouldBeShowed) {
        String screenWeHave = driver.findElement(By.cssSelector(WElement.ACTIVE_PAGE)).getText();
        assertTrue(screenWeHave.equals(screenShouldBeShowed));
    }

    @When("^Click Accept Order.$")
    public void clickAcceptOrder() {
        try {
            Thread.sleep(1500);
            driver.findElement(By.cssSelector(WElement.ACCEPT_NEW_ORDER)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^Order should be converted to Case and \"([^\"]*)\" screen should be displayed.$")
    public void checkCurrentScreenAndConvertationOfOrderToCase(String pageShouldBe) {
        try {
            Thread.sleep(1000);
            String activePageWeHave = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(WElement.ACTIVE_PAGE))).getText();
            int indexOfCaseAccession = 1;
            caseAccession = driver
                    .findElement(By.cssSelector(WElement.CASE_ACCESSION_ON_UPDATE_CASE_PAGE))
                    .getText()
                    .split(" ")[indexOfCaseAccession]
                    .trim();
            assertTrue(activePageWeHave.equals(pageShouldBe));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Under Test Screen section, select concentration for 'Compound1' such that test results are \"([^\"]*)\".$")
    public void checkTestResultForCompound1(String resultTestShouldBe) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector(WElement.COMPOUND_1_CONCENTRATION)).sendKeys("2");
        driver.findElement(By.cssSelector(WElement.COMPOUND_2_CONCENTRATION)).sendKeys(Keys.TAB);

        String resultTestWeHave = driver
                .findElement(By.cssSelector(WElement.RESULT_TEST_COMMENTS_FOR_COMPOUND_1))
                .getAttribute("value");
        assertTrue(resultTestWeHave.equals(resultTestShouldBe));
    }

    @Then("^Selections should be made as appropriately for 'Compound1'.$")
    public void findFieldTestResultCompound1() {
        boolean isResultTestForCompound1Showed = driver
                .findElement(By.cssSelector(WElement.RESULT_TEST_COMMENTS_FOR_COMPOUND_1))
                .isDisplayed();
        assertTrue(isResultTestForCompound1Showed);
    }

    @When("^Under Test Screen section, select concentration for 'Compound2' such that test results are \"([^\"]*)\".$")
    public void checkTestResultCompound2(String resultTestShouldBe) {
        driver.findElement(By.cssSelector(WElement.COMPOUND_2_CONCENTRATION)).sendKeys("5");
        driver.findElement(By.cssSelector(WElement.COMPOUND_2_CONCENTRATION)).sendKeys(Keys.TAB);
        String resultTestWeHave = driver
                .findElement(By.cssSelector(WElement.RESULT_TEST_COMMENTS_FOR_COMPOUND_2))
                .getAttribute("value");
        assertTrue(resultTestWeHave.equals(resultTestShouldBe));
    }

    @Then("^Selections should be made as appropriately for 'Compound2'.$")
    public void findFieldTestResultCompound2() {
        boolean isResultTestForCompound1Showed = driver
                .findElement(By.cssSelector(WElement.RESULT_TEST_COMMENTS_FOR_COMPOUND_2))
                .isDisplayed();
        assertTrue(isResultTestForCompound1Showed);
    }

    @When("^Under Validity Testing section, select concentration for 'VCompound1' in such a way that test results are \"([^\"]*)\".$")
    public void checkTestResultForVCompound1(String resultTestShouldBe) {
        driver.findElement(By.cssSelector(WElement.VCOMPOUND_1_CONCENTRATION)).sendKeys("0");
        driver.findElement(By.cssSelector(WElement.COMPOUND_2_CONCENTRATION)).sendKeys(Keys.TAB);
        String resultTestWeHave = driver
                .findElement(By.cssSelector(WElement.RESULT_TEST_COMMENTS_FOR_VCOMPOUND_1))
                .getAttribute("value");
        assertTrue(resultTestWeHave.equals(resultTestShouldBe));
    }

    @Then("^Selections should be made as appropriately for 'VCompound1'.$")
    public void findFieldTestResultVCompound1() {
        boolean isResultTestForCompound1Showed = driver
                .findElement(By.cssSelector(WElement.RESULT_TEST_COMMENTS_FOR_VCOMPOUND_1))
                .isDisplayed();
        assertTrue(isResultTestForCompound1Showed);
    }

    @When("^Under Validity Testing section, select concentration for 'VCompound2' in such a way that test results are \"([^\"]*)\".$")
    public void checkTestResultVCompound2(String resultTestShouldBe) {
        driver.findElement(By.cssSelector(WElement.VCOMPOUND_2_CONCENTRATION)).sendKeys("5");
        driver.findElement(By.cssSelector(WElement.COMPOUND_2_CONCENTRATION)).sendKeys(Keys.TAB);
        String resultTestWeHave = driver
                .findElement(By.cssSelector(WElement.RESULT_TEST_COMMENTS_FOR_VCOMPOUND_2))
                .getAttribute("value");
        assertTrue(resultTestWeHave.equals(resultTestShouldBe));
    }

    @Then("^Selections should be made as appropriately for 'VCompound2'.$")
    public void findFieldTestResultVCompound2() {
        boolean isResultTestForCompound1Showed = driver
                .findElement(By.cssSelector(WElement.RESULT_TEST_COMMENTS_FOR_VCOMPOUND_2))
                .isDisplayed();
        assertTrue(isResultTestForCompound1Showed);
    }

    @When("^Click Update.$")
    public void clickUpdateOrder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(WElement.UPDATE_ORDER))).click();
    }

    @Then("^Case List screen should be populated with Case 'Status' as \"([^\"]*)\".$")
    public void checkStatusOfOrder(String statusShouldBe) {
        try {
            Thread.sleep(2500);
            int indexOfColumnWithStatus = 6;

            driver.findElement(By.cssSelector(WElement.SEARCH_ORDER_FIELD)).sendKeys(caseAccession);
            String statusWeHave = driver.findElement(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                    .findElements(By.cssSelector(WElement.ONE_COLUMN_IN_ROW))
                    .get(indexOfColumnWithStatus)
                    .getText();
            assertTrue(statusWeHave.equals(statusShouldBe));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Select 'Tasks' from the top menu.$")
    public void selectTasks() {
        driver.findElement(By.cssSelector(WElement.TASKS)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("See All Cases"))).click();
    }

    @Then("^Above Case should be listed under 'Cases in Pending' section.$")
    public void checkAboveCaseUnderCaseInPending() {

        int indexOfCaseNumber = 0;
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(WElement.SEARCH_ORDER_FIELD))).sendKeys(caseAccession);
        try {
            Thread.sleep(1500);
            String caseNumberWeHave = driver.findElement(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                    .findElements(By.cssSelector(WElement.ONE_COLUMN_IN_ROW))
                    .get(indexOfCaseNumber)
                    .getText();
            assertTrue(caseAccession.equals(caseNumberWeHave));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @When("^Select Case # from the 'Cases in Pending' list.$")
    public void selectCase() {
        int indexOfCaseNumber = 0;

        driver.findElement(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                .findElements(By.cssSelector(WElement.ONE_COLUMN_IN_ROW))
                .get(indexOfCaseNumber)
                .findElement(By.id(WElement.CASE_NUMBER))
                .click();
    }

    @Then("^\"([^\"]*)\" screen is displayed.$")
    public void isUpdateScreenShowed(String activePageShouldBe) {
        String activePageWeHave = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(WElement.ACTIVE_PAGE))).getText();
        assertTrue(activePageWeHave.equals(activePageShouldBe));
    }

    @When("^Select 'Finalized' radio option.$")
    public void selectFinalizedRadioOption() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath(WElement.FINALIZED_RADIO_OPTION)).click();
    }

    @Then("A confirmation message \"([^\"]*)\" should be displayed.")
    public void checkConfirmationMessage(String confirmationMessageShouldBe) {
        String confirmationMessageWeHave = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(WElement.CONFIRMATION_MESSAGE)))
                .getText();
        assertTrue(confirmationMessageWeHave.equals(confirmationMessageShouldBe));
    }

    @When("^Click Finalize and enter Sign Pin when it prompts for Sign Pin.$")
    public void finalizeAndEnterPin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(WElement.CLOSE_POPUP_WINDOW))).click();

        String pin = "2016";
        driver.findElement(By.cssSelector(WElement.FINALIZE_BUTTON)).click();
        driver.findElement(By.cssSelector(WElement.PIN_PLACEHOLDER)).sendKeys(pin);
        driver.findElement(By.cssSelector(WElement.APPLY_SIGNATURE_BUTTON)).click();
    }

    @Then("^Case should be finalized successfully.$")
    public void checkCaseStatus() {
        int indexOfStatus = 6;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(WElement.SEARCH_ORDER_FIELD)))
                .sendKeys(caseAccession);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String statusWeHave = driver
                .findElement(By.cssSelector(WElement.ONE_ROW_IN_ORDER_LIST))
                .findElements(By.cssSelector(WElement.ONE_COLUMN_IN_ROW))
                .get(indexOfStatus)
                .getText();
        assertTrue(statusWeHave.equals("finalized"));
    }

    /**
     * Verify Report
     **/


    @After
    public void close(){
        driver.close();
    }
}
