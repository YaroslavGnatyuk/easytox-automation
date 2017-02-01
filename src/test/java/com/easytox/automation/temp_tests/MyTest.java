package com.easytox.automation.temp_tests;

import com.easytox.automation.steps.security.reports.TextPositionSequence;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyTest {
    private static final String downloadFilepath = "home/yroslav/test";
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String easytoxAddress = "http://bmtechsol.com:8080/easytox/";
    private PDDocument report;

    @Before
    public void init() {
//        DriverBase.instantiateDriverObject();
//        driver = DriverBase.getDriver();
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_linux");
        DesiredCapabilities capabilities = getChromePreferences();

        driver = new ChromeDriver(capabilities);
        driver.navigate().to(easytoxAddress);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 35);
    }

    @Test
    public void findAllCasesWithStatusFinalized() { // I'm on cases page
        try {
//            createTempDir(downloadFilepath);

            driver.findElement(By.name("j_username")).sendKeys("PathOne");
            driver.findElement(By.name("j_password")).sendKeys("Test@123");
            driver.findElement(By.cssSelector("button.btn.btn-md.btn-primary")).click();

            Thread.sleep(5000);

//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#caseorder > tbody > tr:nth-child(3) > td:nth-child(9) > form > a"))).click();
//            Thread.sleep(1000);
//            report = downloadReport();
//
//            checkPositionOfLabNameAndLabAddress();
//            checkPersonalData();
            String caseAccession = "AA17-130";
            driver.findElement(By.cssSelector(WElement.SEARCH_ORDER_FIELD)).sendKeys(caseAccession);
            Thread.sleep(5000);
            driver.findElement(By.id("editlink")).click();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void checkPositionOfLabNameAndLabAddress() {
        String labName = "Sujana LabOne";
        String labAddressLine1 = "1234 Tuttles park drive Dublin";
        String labAddressLine2 = "Ohio USA 43016";
        String labAddressLine3 = "Lab Director:     CLIA ID:";

        try {
            List<TextPositionSequence> posLabName = findSubWords(report, 1, labName);

            List<TextPositionSequence> posLabAddress1 = findSubWords(report, 1, labAddressLine1);
            List<TextPositionSequence> posLabAddress2 = findSubWords(report, 1, labAddressLine2);
            List<TextPositionSequence> posLabAddress3 = findSubWords(report, 1, labAddressLine3);

            System.out.println(posLabName.get(0).getX() + "   " + posLabName.get(0).getY());
            System.out.println(posLabAddress1.get(0).getX() + "   " + posLabAddress1.get(0).getY());
            System.out.println(posLabAddress1.get(0).getX() + "   " + posLabAddress2.get(0).getY());
            System.out.println(posLabAddress1.get(0).getX() + "   " + posLabAddress3.get(0).getY());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void checkPersonalData() {
        try {
            PDFTextStripper printer = new PDFTextStripper();
            printer.setSortByPosition(true);
            System.out.println(printer.getText(report));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PDDocument downloadReport() {
        try {
            String reportPath = getPDFFile(downloadFilepath).getPath();
            return PDDocument.load(reportPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DesiredCapabilities getChromePreferences() {
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        return cap;
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

    private void deleteTempDir(String path) {
        File dir = new File(path);
        if (dir.exists()) {

            File[] allFiles = dir.listFiles();
            if (allFiles.length != 0) {
                Arrays.asList(allFiles).stream().forEach(File::delete);
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
        System.out.println(files.size());

        return files.get(0);
    }

    static List<TextPositionSequence> findSubWords(PDDocument document, int page, String searchTerm) throws IOException {
        final List<TextPositionSequence> hits = new ArrayList<TextPositionSequence>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                TextPositionSequence word = new TextPositionSequence(textPositions);
                String string = word.toString();

                int fromIndex = 0;
                int index;
                while ((index = string.indexOf(searchTerm, fromIndex)) > -1) {
                    hits.add(word.subSequence(index, index + searchTerm.length()));
                    fromIndex = index + 1;
                }
                super.writeString(text, textPositions);
            }
        };

        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(document);
        return hits;
    }

    @After
    public void close() {
        driver.close();
        deleteTempDir(downloadFilepath);
    }
}
