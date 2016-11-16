package com.easytox.automation.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WebElementHelper {

    public void visitHomePage(WebDriver driver) {
        driver.navigate().to("");
    }

    public void reloadCurrentPage(WebDriver driver, String url) {
        driver.navigate().to("");
    }

    // Click on Hyperlink 'Patient Add','PATIENT_ADD'
    public WebElement getWebelement(WebDriver driver, String description, String elementName) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(elementName);
        return WebDriverUtils.findElement(driver, constant, null);
    }

    public void clickOnHypderlink(WebDriver driver, String description, String elementName) {
        WebElement webElement = getWebelement(driver, description, elementName);
        webElement.click();
    }

    // Click on Hyperlink 'Patient Add','PATIENT_ADD'
    public void clickOnHyperlink(WebDriver driver, String description, String text) {
        WebDriverUtils.findElementByLinkText(driver, text);
    }

    // Select Hyperlink 'Patient link in Patients Tab','HOME_PATIENT_TAB_DATA_TABLE'
    public void selectHyperlink(WebDriver driver, String description, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.findElement(driver, constant, null);
        webElement.click();
    }

    // Click on Button 'Patient Save','PATIENT_SAVE'
    public void clickButton(WebDriver driver, String description, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.findElement(driver, constant, null);
        webElement.click();
    }

    // Enter Value in 'First Name','Dinesh','PATIENT_FIRST_NAME'
    public void enterText(WebDriver driver, String description, String value, String element) throws Exception {
        // Thread.sleep(2000);
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.findElement(driver, constant, null);
        webElement.clear();
        webElement.sendKeys(value);
    }


    public void enterRandomText(WebDriver driver, String description, String fieldName, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.findElement(driver, constant, null);
        String value = WebDriverUtils.populateText(fieldName);
        webElement.clear();
        webElement.sendKeys(value);
    }


    // Select Dropdown by value 'Drug','WARFARING','DRUG_NAME'
    public void selectOption(WebDriver driver, String description, String value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebDriverUtils.selectDropdownByValue(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant), value);
    }

    // Select Radio Button 'Approve in Edit Lab','LAB_APPROVE'
    public void selectRandomOption(WebDriver driver, String description, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.selectRadioButtonRandomly(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant));
        webElement.click();
    }

    // Select Radio Button by value 'Approve in Edit Lab','Yes','LAB_APPROVE'
    public void selectRadioButton(WebDriver driver, String description, String value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.selectRadioButtonByValue(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant), value);
        webElement.click();
    }

    // Select Checkbox by value 'Add Education','100','ADD_EDUCATION'
    public void selectCheckbox(WebDriver driver, String description, String value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.selectCheckboxByValue(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant), value);
        webElement.click();
    }

    // Select Checkbox 'Add Education','ADD_EDUCATION'
    public void selectCheckbox(WebDriver driver, String description, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.selectCheckboxRandomly(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant));
        webElement.click();
    }

    public void selectDate(WebDriver driver, String description, String element) throws Exception {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.selectCheckboxRandomly(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant));
        webElement.click();
        Thread.sleep(2000);
    }

    // Select Dropdown by text 'Drug','Warfarin','DRUG_NAME'
    public void selectOptionByText(WebDriver driver, String description, String value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebDriverUtils.selectDropdownByText(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant), value);
    }

    // Select Dropdown randomly
    public void selectOptionRandom(WebDriver driver, String description, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebDriverUtils.selectDropdownByIndex(driver, constant, null);
    }

    // Select Dropdown by index 'Drug','1','DRUG_NAME'
    public void selectOptionByIndex(WebDriver driver, String description, int value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebDriverUtils.selectDropdownByIndex(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant), value);
    }

    public void selectFutureDate(WebDriver driver, String description, String value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.findElement(driver, constant, null);
        String date = WebDriverUtils.selectDateFuture(Integer.parseInt(value));
        webElement.sendKeys(date);
    }

    public void enterDatePast(WebDriver driver, String description, String value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.findElement(driver, constant, null);
        String date = WebDriverUtils.selectDatePast(Integer.parseInt(value));
        webElement.sendKeys(date);
    }

    // Select Date 'Encounter Date', '+/-90d','ENCOUNTER_DATE'
    public void selectDate(WebDriver driver, String description, String value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.selectDateRandom(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant));
        webElement.sendKeys(value);
    }

    // Enter Date 'Encounter Date','yyyy/MM/dd','ENCOUNTER_DATE'
    public void enterDate(WebDriver driver, String description, String value, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.findElement(driver, constant, null);
        webElement.sendKeys(value);
    }

    // Assert text exists 'First Name','Dinesh','FIRST_NAME_TEXT'
    public void assertText(WebDriver driver, String description, String message, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement webElement = WebDriverUtils.findElement(driver, constant, null);
        driver.manage().timeouts().implicitlyWait(20, SECONDS);
        assert webElement.getText().contains(message);
    }

    // Assert text doesn't exist 'First Name','Dinesh', 'FIRST_NAME_TEXT'
    public void assertNotText(WebDriver driver, String description, String message, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebElement text = WebDriverUtils.findElement(driver, constant, null);
        assert text.getText() != message;
    }

    public void selectFirstOption(WebDriver driver, String description, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebDriverUtils.selectDropdownByIndexExcludingFirstElement(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant));
    }

    // Verify the message in the dialog box  'message'
    public void assertText(WebDriver driver, String description, String message) {
        WebDriverUtils.confirmAlertBoxText(driver, message);
    }

    // Click ok or cancel in dialog box 'create appointment dialog box','ok'
    public void clickOkButton(WebDriver driver, String description, String action) {
        WebDriverUtils.cofirmAlertBoxAction(driver, action);
    }

    //Enter the text randomly 'First Name','PP_CREATE_FIRSTNAME'
    public void enterText(WebDriver driver, String description, String element) {
        Map<String, LocatorType> constant = AutomationUtils.INSTANCE.getReferenceToConstant(element);
        WebDriverUtils.enterAutoText(driver, AutomationUtils.INSTANCE.getLocator(constant), AutomationUtils.INSTANCE.getLocatorType(constant));
    }

}
