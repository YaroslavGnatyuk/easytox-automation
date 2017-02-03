package com.easytox.automation.temp_tests;

import com.easytox.automation.steps.security.reports.PDFOrder;
import com.easytox.automation.steps.security.reports.WebOrder;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyTest {
    //linux path - /home/yroslav/temp, windows - C:\easy_tox_temp\
    private static final String downloadFilepath = "/home/yroslav/temp";
    private static final String easytoxAddress = "http://bmtechsol.com:8080/easytox/";

    private Logger log = Logger.getLogger(MyTest.class);


    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_linux");
//        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

        DesiredCapabilities capabilities = getChromePreferences();

        driver = new ChromeDriver(capabilities);
        driver.navigate().to(easytoxAddress);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 35);
    }

    @Test
    public void entryPoint() { // I'm on cases page

        try {
            driver.findElement(By.name("j_username")).sendKeys("PathOne");
            driver.findElement(By.name("j_password")).sendKeys("Test@123");
            driver.findElement(By.cssSelector("button.btn.btn-md.btn-primary")).click();
            Thread.sleep(1500);

            String caseAccession = "AA17-050";

            driver.findElement(By.cssSelector(WElement.SEARCH_ORDER_FIELD)).sendKeys(caseAccession);
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.REPORT_DOWNLOAD_ICON)).click();
            Thread.sleep(1000);
            PDDocument order = readPDFWithOrder(caseAccession);
            PDFOrder pdfOrder= new PDFOrder(order);
            pdfOrder = pdfOrder.getOrderFromPDF();

            driver.findElement(By.cssSelector("#caseorder_filter > label > input")).clear();
            driver.findElement(By.cssSelector("#caseorder_filter > label > input")).sendKeys(caseAccession);
            Thread.sleep(1000);
            driver.findElement(By.id("editlink")).click();
            Thread.sleep(1000);
            WebOrder webOrder = new WebOrder(driver, caseAccession);
            webOrder = webOrder.getOrderFromWeb();

            log.info(" From PDF: " + pdfOrder + "\n");
            log.info(" From Web: " + webOrder + "\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private PDDocument readPDFWithOrder(String accessionNumber) {
        PDDocument report = downloadReport(accessionNumber);
        deleteTempDirAndInnerFiles(downloadFilepath);

        return report;
    }

    private DesiredCapabilities getChromePreferences() {
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("test-type");

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability("chrome.binary", "./drivers/chromedriver.exe");
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        return cap;
    }

    private PDDocument downloadReport(String accessionNumber) {
        try {
            createTempDir(downloadFilepath);

            String reportPath = getPDFFile(downloadFilepath).getPath();
            return PDDocument.load(reportPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createTempDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                System.out.println("Directory have created");
            } else {
                System.out.println("Directory have not created");
            }
        }
    }

    private void deleteTempDirAndInnerFiles(String path) {
        File dir = new File(path);
        if (dir.exists()) {

            File[] allFiles = dir.listFiles();
            if (allFiles != null && allFiles.length != 0) {
                Arrays.stream(allFiles).forEach(File::delete);
            }

            if (dir.delete()) {
                System.out.println("Directory have deleted");
            } else {
                System.out.println("Directory have not deleted");
            }
        }
    }

    private File getPDFFile(String path) {
        File dir = new File(path);
        List<File> files = Arrays.asList(dir.listFiles());
        return files.get(0);
    }

    @After
    public void close() {
        driver.close();
    }
}
