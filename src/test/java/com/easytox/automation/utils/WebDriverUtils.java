package com.easytox.automation.utils;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class WebDriverUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static WebElement findElement(WebDriver driver, Map<String, LocatorType> elementMap, int waitForSeconds, WebElement element) {
        return findElement(driver, elementMap, waitForSeconds, element);
    }

    public static WebElement findElement(WebDriver driver, Map<String, LocatorType> elementMap, WebElement element) {
        return findElement(driver, AutomationUtils.INSTANCE.getLocator(elementMap), AutomationUtils.INSTANCE.getLocatorType(elementMap), 80, element);
    }
    public static WebElement findElement(WebDriver driver, String locator, LocatorType locatorType, WebElement element) {
        return findElement(driver, locator, locatorType, 80, element);
    }

    public static WebElement findElement(WebDriver driver, final String locator, LocatorType locatorType, int waitForSeconds, WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(waitForSeconds, TimeUnit.SECONDS)
                .pollingEvery(20, TimeUnit.MILLISECONDS)
                .ignoring(org.openqa.selenium.NoSuchElementException.class);

        final WebDriverWait webDriverWait = new WebDriverWait(driver, 150);

        switch (locatorType) {
            case XPATH:
                wait.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver webDriver) {
                        return webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                    }
                });
                break;
            case CLASS:
                wait.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver webDriver) {
                        return webDriverWait.until(ExpectedConditions.elementToBeClickable(By.className(locator)));
                    }
                });
                break;
            case ID:
                wait.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver webDriver) {
                        return webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id(locator)));
                    }
                });
                break;
            case TAG:
                wait.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver webDriver) {
                        return webDriverWait.until(ExpectedConditions.elementToBeClickable(By.tagName(locator)));
                    }
                });
                break;
            case NAME:
                wait.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver webDriver) {
                        return webDriverWait.until(ExpectedConditions.elementToBeClickable(By.name(locator)));
                    }
                });
                break;
        }
        return null;
    }

    public static List<WebElement> findElements(WebDriver driver, Map<String, LocatorType> elementMap, WebElement element) {
        return findElements(driver, elementMap, element);
    }

    public static List<WebElement> findElements(final WebDriver driver, final String locator, LocatorType locatorType, final WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(80, TimeUnit.SECONDS)
                .pollingEvery(10, TimeUnit.MILLISECONDS)
                .ignoring(org.openqa.selenium.NoSuchElementException.class);


        switch (locatorType) {
            case XPATH:
                wait.until(new Function<WebDriver, List<WebElement>>() {
                    public List<WebElement> apply(WebDriver webDriver) {
                        if (element != null) {
                            return element.findElements(By.xpath(locator));
                        }
                        return driver.findElements(By.xpath(locator));
                    }
                });
                break;
            case CLASS:
                wait.until(new Function<WebDriver, List<WebElement>>() {
                    public List<WebElement> apply(WebDriver webDriver) {
                        if (element != null) {
                            return element.findElements(By.className(locator));
                        }
                        return driver.findElements(By.className(locator));
                    }
                });
                break;
            case ID:
                wait.until(new Function<WebDriver, List<WebElement>>() {
                    public List<WebElement> apply(WebDriver webDriver) {
                        if (element != null) {
                            return element.findElements(By.id(locator));
                        }
                        return driver.findElements(By.id(locator));
                    }
                });
                break;
            case TAG:
                wait.until(new Function<WebDriver, List<WebElement>>() {
                    public List<WebElement> apply(WebDriver webDriver) {
                        if (element != null) {
                            return element.findElements(By.tagName(locator));
                        }
                        return driver.findElements(By.tagName(locator));
                    }
                });
                break;
            case NAME:
                wait.until(new Function<WebDriver, List<WebElement>>() {
                    public List<WebElement> apply(WebDriver webDriver) {
                        if (element != null) {
                            return element.findElements(By.name(locator));
                        }
                        return driver.findElements(By.name(locator));
                    }
                });
                break;
        }
        return null;
    }


    public static void clearAndSendKeys(WebDriver driver, Map<String, LocatorType> elementMap, String value) {
        clearAndSendKeys(driver, elementMap, value);
    }

    public static void clearAndSendKeys(WebDriver driver, String locator, LocatorType locatorType, String value) {
        WebElement webElement = findElement(driver, locator, locatorType, null);
        webElement.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), "");
        webElement.sendKeys(value);
    }

    public static void selectDropdownByIndex(WebDriver driver, Map<String, LocatorType> elementMap, Integer optionIndex) {
        selectDropdownByIndex(driver, elementMap, optionIndex);
    }

    public static void selectDropdownByIndex(WebDriver driver, String locator, LocatorType locatorType, Integer optionIndex) {
        WebElement element = findElement(driver, locator, locatorType, null);
        Select select = new Select(element);
        if (optionIndex != null) {
            select.selectByIndex(optionIndex);
        }
        Random random = new Random();
        optionIndex = random.nextInt(select.getOptions().size() - 1);
        select.selectByIndex(optionIndex++);
        // Thread.sleep(2000);
    }

    public static void selectDropdownByValue(WebDriver driver, Map<String, LocatorType> elementMap, String value) {

    }

    public static void selectDropdownByValue(WebDriver driver, String locator, LocatorType locatorType, String value) {
        WebElement element = findElement(driver, locator, locatorType, null);
        Select select = new Select(element);
        select.selectByValue(value);
        // Thread.sleep(2000);
    }

    public static void selectDropdownByText(WebDriver driver, Map<String, LocatorType> elementMap, String text) {
        selectDropdownByText(driver, elementMap, text);
    }

    public static void selectDropdownByText(WebDriver driver, String locator, LocatorType locatorType, String text) {
        WebElement element = findElement(driver, locator, locatorType, null);
        Select select = new Select(element);
        select.selectByVisibleText(text);
        // Thread.sleep(2000);
    }

    public static WebElement selectRadioButtonRandomly(WebDriver driver, Map<String, LocatorType> elementMap) {
        return selectRadioButtonRandomly(driver, AutomationUtils.INSTANCE.getLocator(elementMap), AutomationUtils.INSTANCE.getLocatorType(elementMap));
    }

    public static WebElement selectRadioButtonRandomly(WebDriver driver, String locator, LocatorType locatorType) {
        List<WebElement> radioGroup = findElements(driver, locator, locatorType, null);
        Random random = new Random();
        int index = random.nextInt(radioGroup.size());
        return radioGroup.get(index);
    }

    public static WebElement selectRadioButtonByValue(WebDriver driver, Map<String, LocatorType> elementMap) {
        return selectRadioButtonByValue(driver, AutomationUtils.INSTANCE.getLocator(elementMap), AutomationUtils.INSTANCE.getLocatorType(elementMap), null);
    }

    public static WebElement selectRadioButtonByValue(WebDriver driver, String locator, LocatorType locatorType, String Value) {
        List<WebElement> radioGroup = findElements(driver, locator, locatorType, null);
        int size = radioGroup.size();
        for (int i = 0; i < size; i++) {
            String isValue = radioGroup.get(i).getAttribute("value");
            if (isValue.equalsIgnoreCase(Value)) {
                return radioGroup.get(i);
            }
        }
        return null;
    }

    public static WebElement selectCheckboxByValue(WebDriver driver, Map<String, LocatorType> elementMap) {
        return selectCheckboxByValue(driver, AutomationUtils.INSTANCE.getLocator(elementMap), AutomationUtils.INSTANCE.getLocatorType(elementMap), null);
    }

    public static WebElement selectCheckboxByValue(WebDriver driver, String locator, LocatorType locatorType, String Value) {
        List<WebElement> checkBoxGroup = findElements(driver, locator, locatorType, null);
        int size = checkBoxGroup.size();
        for (int i = 0; i < size; i++) {
            String isValue = checkBoxGroup.get(i).getAttribute("value");
            if (isValue.equalsIgnoreCase(Value)) {
                return checkBoxGroup.get(i);
            }
        }
        return null;
    }

    public static WebElement selectCheckboxRandomly(WebDriver driver, Map<String, LocatorType> elementMap) {
        return selectCheckboxRandomly(driver, AutomationUtils.INSTANCE.getLocator(elementMap), AutomationUtils.INSTANCE.getLocatorType(elementMap));
    }

    public static WebElement selectCheckboxRandomly(WebDriver driver, String locator, LocatorType locatorType) {
        List<WebElement> checkBoxGroup = findElements(driver, locator, locatorType, null);
        Random random = new Random();
        int index = random.nextInt(checkBoxGroup.size());
        return checkBoxGroup.get(index);
    }

    public static WebElement selectDateRandom(WebDriver driver, Map<String, LocatorType> elementMap) {
        return selectDateRandom(driver, AutomationUtils.INSTANCE.getLocator(elementMap), AutomationUtils.INSTANCE.getLocatorType(elementMap));
    }

    public static WebElement selectDateRandom(WebDriver driver, String locator, LocatorType locatorType) {
        List<WebElement> datePicker = findElements(driver, locator, locatorType, null);
        Random random = new Random();
        int index = random.nextInt(datePicker.size());
        return datePicker.get(index);
//        Thread.sleep(100);
    }

    public static String selectDateFuture(int noOfDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, noOfDays);
        return sdf.format(c.getTime());
    }

    public static String selectDatePast(int noOfDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -(noOfDays));
        return sdf.format(c.getTime());
    }

//    public WebElement getWhenVisible(By locator, int timeout) {
//        WebElement element = null;
//        WebDriverWait wait = new WebDriverWait(driver, timeout);
//        element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//        return element;
//    }
//
//    public void clickWhenReady(By locator, int timeout) {
//        WebDriverWait wait = new WebDriverWait(driver, timeout);
//        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
//        element.click();
//    }

    public static void selectDropdownByIndexExcludingFirstElement(WebDriver driver, Map<String, LocatorType> elementMap) {
        selectDropdownByIndexExcludingFirstElement(driver, AutomationUtils.INSTANCE.getLocator(elementMap), AutomationUtils.INSTANCE.getLocatorType(elementMap));
    }

    public static void selectDropdownByIndexExcludingFirstElement(WebDriver driver, String locator, LocatorType locatorType) {
        WebElement element = findElement(driver, locator, locatorType, null);
        int optionIndex;
        Random random = new Random();
        Select select = new Select(element);
        optionIndex = random.nextInt(select.getOptions().size() - 1);
        select.selectByIndex(++optionIndex);
        // Thread.sleep(2000);
    }

    public static void confirmAlertBoxText(WebDriver driver, String message) {
        // Thread.sleep(1000);
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        assert text.contains(message);
    }

    public static void cofirmAlertBoxAction(WebDriver driver, String action) {
        Alert alert = driver.switchTo().alert();
        if (action.equals("ok")) {
            alert.accept();
        } else {
            alert.dismiss();
        }
    }

    public static void findElementByLinkText(WebDriver driver, String text) {
        WebElement element = driver.findElement(By.linkText(text));
        element.click();
    }

    public static void enterAutoText(WebDriver driver, String locator, LocatorType locatorType) {
        String[] arr = UUID.randomUUID().toString().split("-");
        WebElement element = findElement(driver, locator, locatorType, null);
        element.sendKeys(arr[0]);
    }

    public static void enterAutoText(WebDriver driver, Map<String, LocatorType> elementMap) {
        String[] arr = UUID.randomUUID().toString().split("-");
        WebElement element = findElement(driver, elementMap, null);
        element.sendKeys(arr[0]);
    }

    public static String populateText(String fieldName) {
        if (fieldName.equals("getFirstName")) {
            return SampleDataUtil.getFirstName();
        } else {
            return SampleDataUtil.getLastName();
        }
    }

}

