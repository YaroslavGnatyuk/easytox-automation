package com.easytox.automation.steps.security.password_reset.case_7;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class VerifyDataSortingSteps {
    private WebDriver driver;
    private Logger log = Logger.getLogger(VerifyDataSortingSteps.class);
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String pageAfterSuccessfulLogging = "http://bmtechsol.com:8080/easytox/lab/payment"; //This address we have after successful logging

    private List<String> unsortedColumns;
    private List<String> sortedColumns;

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @When("^Login to Easytox with \"([^\"]*)\" and \"([^\"]*)\" credentials.$")
    public void loginToEasyTox(String username, String password) {
        driver.navigate().to(easyToxAddress);
        driver.findElement(By.name(WElement.loginPage_fieldName)).sendKeys(username);
        driver.findElement(By.name(WElement.loginPage_passwordField)).sendKeys(password);
        driver.findElement(By.cssSelector(WElement.loginPage_loginButton)).click();
    }

    @Then("^User login should be successful.$")
    public void checkCurrentPage() {
        try {
            assertTrue(driver.getCurrentUrl().equals(pageAfterSuccessfulLogging));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click \"Pending Password Requests\" link.$")
    public void clickOnThePendingPasswordRequest() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.pendingPasswordRequest)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @And("^Select \"See All Requests\".$")
    public void selectAllRequest() {
        driver.findElement(By.linkText(WElement.textLinkSeeAllRequest)).click();
    }

    @Then("^Password Request List\" screen should be displayed.$")
    public void isPasswordRequestListShowed() {
        try {
            Thread.sleep(1000);
            String tableNameShouldBe = "Request List";
            String tableNameWeHave = driver.findElement(By.cssSelector(WElement.tableName)).getText();

            assertTrue(tableNameWeHave.equals(tableNameShouldBe));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click on down arrow icon  on each column name(.*).$")
    public void clickOnDownArrowIcon(String column) {

        new Select(driver.findElement(By.cssSelector(WElement.requestPerPage))).selectByVisibleText("All");

        unsortedColumns = getTextOfOneColumnFromTableBody(column);

        List<WebElement> elements = driver
                .findElements(By.cssSelector(WElement.columnsName));
        for (WebElement oneColumn : elements) {
            if(oneColumn.getText().equals(column)){
                oneColumn.click();
            }
        }

        sortedColumns = getTextOfOneColumnFromTableBody(column);
        if (column.equals("Requested Date")) {
            Collections.sort(unsortedColumns, new SortAscendDate()); //I imitate sorting by column in table
        } else {
            Collections.sort(unsortedColumns, new SortAscendIgnoreCase()); //I imitate sorting by column in table
        }
    }

    @Then("^Records should be displayed based on the ascending order of the select column.$")
    public void checkResultOfAscendingOrder() {
        for (int i = 0; i < unsortedColumns.size(); i++) {
            assertTrue(unsortedColumns.get(i).equals(sortedColumns.get(i)));
        }
    }

    @When("^Click on Up arrow icon on each column name(.*).$")
    public void clickOnUpArrowIcon(String column) {
        unsortedColumns = getTextOfOneColumnFromTableBody(column);
        List<WebElement> columns = driver
                .findElements(By.cssSelector(WElement.columnsName));

        /**I make sorting by column in table on view**/
        for (WebElement oneColumn : columns) {
            if(oneColumn.getText().equals(column)){
                oneColumn.click();
            }
        }

        sortedColumns = getTextOfOneColumnFromTableBody(column);
        if (column.equals("Requested Date")) {
            Collections.sort(unsortedColumns, new SortDescendDate()); //I imitate sorting by column in table
        } else {
            Collections.sort(unsortedColumns, new SortDescendIgnoreCase()); //I imitate sorting by column in table
        }
    }

    @Then("^Records should be displayed based on the descending order of the selected column.$")
    public void checkResultOfDescendingOrder() {
        for (int i = 0; i < unsortedColumns.size(); i++) {
            assertTrue(unsortedColumns.get(i).equals(sortedColumns.get(i)));
        }
    }

    @After
    public void close() {
        driver.close();
    }

    private List<String> getTextOfOneColumnFromTableBody(String column) {
        /*I get amount of pathologists*/
        int amountOfPathologistsPerPage = Integer.
                valueOf(driver
                        .findElement(By.cssSelector(WElement.amountOfRequest))
                        .getText()
                        .split(" ")[3]);

        log.info(amountOfPathologistsPerPage + "\n");

        List<String> columnsContent = new ArrayList<>(amountOfPathologistsPerPage);

        switch (column) {
            case "Request": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    columnsContent.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(1)")).getText());
                }
                break;
            }

            case "Requested By": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    columnsContent.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(2)")).getText());
                }
                break;
            }

            case "Requested Date": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    columnsContent.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(3)")).getText());
                }
                break;
            }

            case "Requested Email": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    columnsContent.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(4)")).getText());
                }
                break;
            }

            case "Resolved By": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    columnsContent.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(5)")).getText());
                }
                break;
            }

            case "Status": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    columnsContent.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(6)")).getText());
                }
                break;
            }
            default:
                log.info("No case for this column.");
        }
        return columnsContent;
    }

    private class SortAscendDate implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            String sDate1 = (String) o1;
            String sDate2 = (String) o2;

            Date date1 = new Date();
            Date date2 = new Date();

            String dateFormat = "dd/MMM/yyyy HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);

            try {
                date1 = format.parse(sDate1);
                date2 = format.parse(sDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date1.compareTo(date2);
        }
    }

    private class SortDescendDate implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            String sDate1 = (String) o1;
            String sDate2 = (String) o2;

            Date date1 = new Date();
            Date date2 = new Date();

            String dateFormat = "dd/MMM/yyyy HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);

            try {
                date1 = format.parse(sDate1);
                date2 = format.parse(sDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int resultOfCompering = date1.compareTo(date2);

            if (resultOfCompering > 0) return -1;
            if (resultOfCompering < 0) return 1;
            return 0;
        }
    }

    private class SortAscendIgnoreCase implements Comparator<Object> { // I make sorting ignore case because column in table sorts in the same way
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        }
    }

    private class SortDescendIgnoreCase implements Comparator<Object> { // I make sorting ignore case because column in table sorts in the same way
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;
            if (s1.toLowerCase().compareTo(s2.toLowerCase()) > 0) return -1;
            if (s1.toLowerCase().compareTo(s2.toLowerCase()) < 0) return 1;
            return 0;
        }
    }
}
