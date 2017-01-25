package com.easytox.automation.steps.security.reports.case_5_reports;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

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
     *Case finalize
     **/

    @After
    public void close(){
        driver.close();
    }
}
