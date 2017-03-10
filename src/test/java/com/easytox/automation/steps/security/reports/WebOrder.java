package com.easytox.automation.steps.security.reports;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yroslav on 2/3/17.
 */
public class WebOrder extends Order {
    private WebDriver driver;
    private String accessionNumber;

    private static final Logger log = Logger.getLogger(WebOrder.class);

    public WebOrder(final WebDriver driver, String accessionNumber) {
        this.driver = driver;
        this.accessionNumber = accessionNumber;
    }

    public WebOrder() {
    }

    public WebOrder getOrderFromWeb() {
        String[] patient = driver
                .findElement(By.cssSelector("#caseordertab > div.row > div:nth-child(1) > div > div > div:nth-child(1) > div > div > span"))
                .getText()
                .split(",");

        int indexOfName = 0;
        int indexOfDOB = 2;

        String patientName = patient[indexOfName].trim();
        String patientDOB = patient[indexOfDOB].replace("(DOB)", "").trim();
        String dateCollected = driver.findElement(By.cssSelector("#dateCollected")).getAttribute("value");
        String physician = driver.findElement(By.cssSelector("#select2-primaryPhysician-container"))
                .getAttribute("title");
        String sampleType = new Select(driver.findElement(By.cssSelector("#caseType")))
                .getFirstSelectedOption()
                .getText();
        String dateReceived = driver.findElement(By.cssSelector("#datereceived")).getAttribute("value");

        String compound1Result = new Select(driver.findElement(By.cssSelector("#compounds\\5b 0\\5d \\2e result")))
                .getFirstSelectedOption()
                .getText();
        String compound1Cutoff = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(7) > div > div:nth-child(3) > div > input"))
                .getAttribute("value");
        String compound1Concentration = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(7) > div > div:nth-child(4) > div > input"))
                .getAttribute("value");
        String compound1Comments = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(7) > div > div:nth-child(5) > div > div.col-sm-9 > div > input"))
                .getAttribute("value");
        String compound2Result = new Select(driver.findElement(By.cssSelector("#compounds\\5b 1\\5d \\2e result")))
                .getFirstSelectedOption()
                .getText();
        String compound2Cutoff = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(12) > div > div:nth-child(3) > div > input"))
                .getAttribute("value");
        String compound2Concentration = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(12) > div > div:nth-child(4) > div > input"))
                .getAttribute("value");
        String compound2Comments = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(12) > div > div:nth-child(5) > div > div.col-sm-9 > div > input"))
                .getAttribute("value");
        String vCompound1Result = new Select(driver.findElement(By.cssSelector("#validitycompounds\\5b 0\\5d \\2e result")))
                .getFirstSelectedOption()
                .getText();
        String vCompound1ReferenceRange = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(4) > div > div:nth-child(3) > div > input"))
                .getAttribute("value");
        String vCompound1Concentration = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(4) > div > div:nth-child(4) > div > input"))
                .getAttribute("value");
        String vCompound1Comments = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(4) > div > div:nth-child(5) > div > div.col-sm-9 > div > input"))
                .getAttribute("value");
        String vCompound2Result = new Select(driver.findElement(By.cssSelector("#validitycompounds\\5b 1\\5d \\2e result")))
                .getFirstSelectedOption()
                .getText();
        String vCompound2ReferenceRange = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(8) > div > div:nth-child(3) > div > input"))
                .getAttribute("value");
        String vCompound2Concentration = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(8) > div > div:nth-child(4) > div > input"))
                .getAttribute("value");
        String vCompound2Comments = driver
                .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(8) > div > div:nth-child(5) > div > div.col-sm-9 > div > input"))
                .getAttribute("value");
        // TODO: 3/10/17 remove this comment
        /*List<String> drugs = driver
                .findElements(By.className("select2-selection__choice"))
                .stream()
                .map(e -> e.getAttribute("title"))
                .collect(Collectors.toList());*/

        List<WebElement> elements = driver.findElements(By.className("select2-selection__choice"));
        List<String> drugs = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            drugs.add(elements.get(i).getAttribute("title"));
        }

        super.setPatientName(patientName);
        super.setPatientDOB(patientDOB);
        super.setAccessionNumber(accessionNumber);
        super.setCollectDate(dateCollected);
        super.setPhysician(physician);
        super.setSampleType(sampleType);
        super.setReceivedInLab(dateReceived);

        super.setCompound1Result(compound1Result);
        super.setCompound1Cutoff(compound1Cutoff);
        super.setCompound1Concentration(compound1Concentration);
        super.setCompound1Comments(compound1Comments);

        super.setCompound2Result(compound2Result);
        super.setCompound2Cutoff(compound2Cutoff);
        super.setCompound2Concentration(compound2Concentration);
        super.setCompound2Comments(compound2Comments);

        super.setvCompound1Result(vCompound1Result);
        super.setvCompound1ReferenceRange(vCompound1ReferenceRange);
        super.setvCompound1Concentration(vCompound1Concentration);
        super.setvCompound1Comments(vCompound1Comments);

        super.setvCompound2Result(vCompound2Result);
        super.setvCompound2ReferenceRange(vCompound2ReferenceRange);
        super.setvCompound2Concentration(vCompound2Concentration);
        super.setvCompound2Comments(vCompound2Comments);

        super.setMedications(drugs);

        return this;
    }
}
