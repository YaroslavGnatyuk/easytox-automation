/**
 * 
 */
package com.easytox.automation.driver;

import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dhanumandla
 *
 */
//@Listeners(ScreenshotListener.class)
public class DriverBase extends AbstractTestNGCucumberTests {

    private static final Logger LOGGER = Logger.getLogger(DriverBase.class);

    private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<DriverFactory>());
    private static ThreadLocal<DriverFactory> driverFactory;

    @BeforeSuite(alwaysRun = true)
    public static void instantiateDriverObject() {
        driverFactory = new ThreadLocal<DriverFactory>() {
            @Override
            protected DriverFactory initialValue() {
                DriverFactory driverFactory = new DriverFactory();
                webDriverThreadPool.add(driverFactory);
                return driverFactory;
            }
        };
    }

    public static WebDriver getDriver() {
        try {
            return driverFactory.get().getDriver();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public static void clearCookies() throws Exception {
        getDriver().manage().deleteAllCookies();
    }

    @AfterSuite(alwaysRun = true)
    public static void closeDriverObjects() {
        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitDriver();
        }
    }

    public void loggedIn() {
        driverFactory.get().setLoggedIn(true);
    }

    public boolean isLoggedIn() {
        return driverFactory.get().isLoggedIn();
    }
}
