package com.easytox.automation.temp_tests;

import com.easytox.automation.driver.DriverBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yroslav on 12/15/16.
 */
public class MyTest {
    private WebDriver driver;
    private static final String easytoxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String casePage = "http://bmtechsol.com:8080/easytox/caseOrder/list";

    @Before
    public void init() {
        try {
            DriverBase.instantiateDriverObject();
            driver = DriverBase.getDriver();
            driver.navigate().to(easytoxAddress);
            driver.manage().window().maximize();

            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllCasesWithStatusFinalized() { // I'm on cases page
        try {
            driver.findElement(By.name("j_username")).sendKeys("cgilabadmin");
            driver.findElement(By.name("j_password")).sendKeys("Welcome@123");
            driver.findElement(By.cssSelector("button.btn.btn-md.btn-primary")).click();


            List<WebElement> elements = new ArrayList<>();

            Thread.sleep(1500);

            new Select(driver.findElement(By.cssSelector("#caseorder_length > label > select"))).selectByVisibleText("All");
            List<WebElement> elementsOdd = driver.findElements(By.cssSelector("tr.odd"));
            List<WebElement> elementsEven = driver.findElements(By.cssSelector("tr.even"));

            System.out.println(elementsEven.size() + elementsOdd.size());

            for (int i = 0; i < elementsOdd.size(); i++) {
                if (elementsOdd.get(i).findElement(
                        By.cssSelector("td:nth-child(7)"))
                        .getText().equals("finalized")) {
                    elements.add(elementsOdd.get(i));
                }
            }

            for (int i = 0; i < elementsEven.size(); i++) {
                if (elementsEven.get(i).findElement(
                        By.cssSelector("td:nth-child(7)"))
                        .getText().equals("finalized")) {
                    elements.add(elementsEven.get(i));
                }
            }
            System.out.println(elements.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @After
    public void close(){
        driver.close();
    }
}
