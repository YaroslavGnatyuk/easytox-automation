package com.easytox.automation.steps.security.search.cgilabadmin.ET_001_Case_Search;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;

/**
 * Created by yroslav on 12/10/16.
 */
public class CaseSearchSteps {
    private WebDriver driver;
    private static final String easytoxAddress = "http://bmtechsol.com:8080/easytox/";

    @Before
    public void init() {
        try {
            DriverBase.instantiateDriverObject();
            driver = DriverBase.getDriver();

            driver.navigate().to(easytoxAddress);

            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
