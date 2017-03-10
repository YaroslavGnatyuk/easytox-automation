package com.easytox.automation.steps.lab_pathologist.ET_005;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class SortingDataSteps {
    private WebDriver driver;
    private static final String easyToxAddress = "http://bmtechsol.com:8080/easytox/";

    private Logger log = Logger.getLogger(SortingDataSteps.class);

    private List<String> unsortedColumns;
    private List<String> sortedColumns;

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.navigate().to(easyToxAddress);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Given("^User login with the physician \"([^\"]*)\" and password \"([^\"]*)\"$$")
    public void loginToEasyTox(String usr, String psw) {
        try {
            driver.findElement(By.name(WElement.loginPageFieldName)).sendKeys(usr);
            driver.findElement(By.name(WElement.loginPagePasswordField)).sendKeys(psw);
            driver.findElement(By.cssSelector(WElement.loginPageLoginButton)).click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Select Settings -> Lab Pathologist.$")
    public void gotToPathologistPage() {
        try {
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
            assertTrue(pageTitle.equals("Lab Pathologist List"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click on down arrow icon on  Sorting  column (.*)$")
    public void sortDataOnTheAscendingOrder(String column) {
        new Select(driver.findElement(By.cssSelector(WElement.pathologistPerPage))).selectByVisibleText("All");

        unsortedColumns = getTextOfOneColumnFromTableBody(column);

        List<WebElement> elements = driver
                .findElement(By.cssSelector(WElement.rowWithColumnsName))
                .findElements(By.cssSelector(WElement.columnsName));
        // TODO: 3/10/17 remove this comment
//        elements.stream().filter(element -> element.getText().equals(column)).forEach(WebElement::click); //I search "column" and make sorting by column in table on view
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().equals(column)) {
                elements.get(i).click();
            }
        }

        sortedColumns = getTextOfOneColumnFromTableBody(column);
        if (column.equals("Medicaid Num") || column.equals("Medicare Num")) {
            Collections.sort(unsortedColumns, new SortAscendInteger()); //I imitate sorting by column in table
        } else {
            Collections.sort(unsortedColumns, new SortAscendIgnoreCase()); //I imitate sorting by column in table
        }
    }

    @Then("^Records should be displayed based on the ascending order of the selected  field$")
    public void checkTheResultOfAscendingOrder() {
        for (int i = 0; i < sortedColumns.size(); i++) {
            log.info(unsortedColumns.get(i) + " " + sortedColumns.get(i) + "-" + unsortedColumns.get(i).equals(sortedColumns.get(i)));
            assertTrue(unsortedColumns.get(i).equals(sortedColumns.get(i)));
        }
    }

    @When("^Click on Up arrow icon  on sorting column (.*)$")
    public void clickOnUpArrowIconOnSortingColumnSortingColumn(String column) {
        unsortedColumns = getTextOfOneColumnFromTableBody(column);
        List<WebElement> elements = driver
                .findElement(By.cssSelector(WElement.rowWithColumnsName))
                .findElements(By.cssSelector(WElement.columnsName));
        // TODO: 3/10/17 Remove this comment
//        elements.stream().filter(element -> element.getText().equals(column)).forEach(WebElement::click); //I make sorting by column in table on view
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().equals(column)) {
                elements.get(i).click();
            }
        }

        sortedColumns = getTextOfOneColumnFromTableBody(column);
        if (column.equals("Medicaid Num") || column.equals("Medicare Num")) {
            Collections.sort(unsortedColumns, new SortDescendInteger()); //I imitate sorting by column in table
        } else {
            Collections.sort(unsortedColumns, new SortDescendIgnoreCase()); //I imitate sorting by column in table
        }
    }

    @Then("^Records should be displayed based on the descending order of the selected field$")
    public void checkTheResultOfDescendingOrder() {
        for (int i = 0; i < sortedColumns.size(); i++) {
            log.info(unsortedColumns.get(i) + " " + sortedColumns.get(i) + "-" + unsortedColumns.get(i).equals(sortedColumns.get(i)));
            assertTrue(unsortedColumns.get(i).equals(sortedColumns.get(i)));
        }
    }

    @After
    public void close() {
        driver.close();
    }

    private List<String> getTextOfOneColumnFromTableBody(String column) {
        /*I get amount of pathologist at all*/
        int amountOfPathologistsPerPage = Integer.
                valueOf(driver
                        .findElement(By.cssSelector(WElement.amountOfPathologists))
                        .getText()
                        .split(" ")[3]);

        List<WebElement> elements = new ArrayList<>(amountOfPathologistsPerPage);

        switch (column) {
            case "Username": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    elements.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(2)")));
                }
                break;
            }

            case "Name": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    elements.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(3)")));
                }
                break;
            }

            case "Lab": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    elements.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(4)")));
                }
                break;
            }

            case "Salutation": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    elements.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(5)")));
                }
                break;
            }

            case "Medicare Num": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    elements.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(6)")));
                }
                break;
            }

            case "Medicaid Num": {
                for (int i = 0; i < amountOfPathologistsPerPage; i++) {
                    elements.add(driver.findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(7)")));
                }
                break;
            }
            default:
                log.info("No case for this column.");
        }
        List<String> allLinesFromOneColumn = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            allLinesFromOneColumn.add(elements.get(i).getText());
        }
        // TODO: 3/10/17 remove this comment
//        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
        return allLinesFromOneColumn;
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
            int resultOfCompering = s1.toLowerCase().compareTo(s2.toLowerCase());
            if (resultOfCompering > 0) return -1;
            if (resultOfCompering < 0) return 1;
            return 0;
        }
    }

    private class SortAscendInteger implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;

            if (s1.isEmpty() || s2.isEmpty())       //If string is empty I put their in the end of the list
                return -1;

            Integer i1 = Integer.valueOf(s1);
            Integer i2 = Integer.valueOf(s2);
            return i1.compareTo(i2);
        }
    }

    private class SortDescendInteger implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;

            if (s1.isEmpty() || s2.isEmpty())       //If string is empty I put their in the end of the list
                return -1;

            Integer i1 = Integer.valueOf(s1);
            Integer i2 = Integer.valueOf(s2);

            int resultOfCompering = i1.compareTo(i2);

            if (resultOfCompering > 0) return -1;
            if (resultOfCompering < 0) return 1;

            return 0;
        }
    }

}
