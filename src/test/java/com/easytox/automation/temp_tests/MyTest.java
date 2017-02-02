package com.easytox.automation.temp_tests;

import com.easytox.automation.steps.security.reports.Order;
import com.easytox.automation.steps.security.reports.TextPositionSequence;
import org.apache.log4j.Logger;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MyTest {
    private static final String downloadFilepath = "C:\\easy_tox_temp\\";
    private static final String easytoxAddress = "http://bmtechsol.com:8080/easytox/";

    private Logger log = Logger.getLogger(MyTest.class);


    private WebDriver driver;
    private WebDriverWait wait;
    private Order orderFromWeb;
    private Order orderFromPDF;
    private PDDocument report;

    @Before
    public void init() {
//        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_linux");
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        DesiredCapabilities capabilities = getChromePreferences();

        driver = new ChromeDriver(capabilities);
        driver.navigate().to(easytoxAddress);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 35);
    }

    @Test
    public void entryPoint() { // I'm on cases page
        orderFromWeb = new Order();
        orderFromPDF = new Order();

        try {
            driver.findElement(By.name("j_username")).sendKeys("PathOne");
            driver.findElement(By.name("j_password")).sendKeys("Test@123");
            driver.findElement(By.cssSelector("button.btn.btn-md.btn-primary")).click();

            Thread.sleep(1500);

            String caseAccession = "AA17-050";
            orderFromPDF = getOrderFromPDF(caseAccession);

//            orderFromWeb = getOrderFromWeb(caseAccession);

            log.info(" From PDF: " + orderFromPDF + "\n");
//            log.info(" From Web: " + orderFromWeb + "\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private Order getOrderFromPDF(String accessionNumber) {
        createTempDir(downloadFilepath);
        try {
            driver.findElement(By.cssSelector(WElement.SEARCH_ORDER_FIELD)).sendKeys(accessionNumber);
            Thread.sleep(100);
            driver.findElement(By.cssSelector(WElement.REPORT_DOWNLOAD_ICON)).click();
            Thread.sleep(1000);

            report = downloadReport();
            String content = getContentFromReport(report);
            List<String> stringsFromReport = Arrays.asList(content.split("\\r?\\n"));

            return parseStringsFromReport(stringsFromReport);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            deleteTempDir(downloadFilepath);
        }

        return null;
    }

    private Order parseStringsFromReport(List<String> stringsFromReport) {
        Order order = new Order();
        for (int i = 0; i < stringsFromReport.size(); i++) {
            String stringFromReport = stringsFromReport.get(i);

            setPatientNameAndPhysician(stringFromReport, order);
            setAccessionNumber(stringFromReport, order);
            setPatientDOBAndSampleType(stringFromReport, order);
            setCollectedDateAndReceivedInLab(stringFromReport, order);
            setCompound1(stringFromReport, order);
            setCompound2(stringFromReport, order);
            setVCompound1(stringFromReport, order);
            setVCompound2(stringFromReport, order);
            setMedication(stringFromReport, order);
        }
        return order;
    }

    private void setMedication(final String stringFromReport, Order order) {
        String medication = "Medication(s) :";

        if (stringFromReport.contains(medication)) {
            String tempStringFromReport = stringFromReport.replace(medication, "");

            List<String> data = new ArrayList<>();
            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(",")).toArray(String[]::new));


            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            order.setMedications(data);
        }
    }

    private void setVCompound1(final String stringFromReport, Order order) {
        String vCompound1 = "VCompound1 ";

        if (stringFromReport.contains(vCompound1)) {
            String tempStringFromReport = stringFromReport.replace(vCompound1, "");

            List<String> data = new ArrayList<>();
            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(" ")).toArray(String[]::new));

            //I compound two last words here, because I separated those two words in previous line.
            StringBuilder result = new StringBuilder(data.get(data.size() - 2) + " " + data.get(data.size() - 1));
            data.remove(data.size() - 1);
            data.remove(data.size() - 1);
            data.add(result.toString());

            //I'm checking for comparison sign. If we have comparison sign than I compound it with value.
            if (data.size() > 4) {
                StringBuilder twoString = new StringBuilder(data.get(1) + " " + data.get(2));
                data.remove(1);
                data.remove(1);
                data.add(1, twoString.toString());
            }

            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            order.setvCompound1Result(data.get(0));
            order.setvCompound1ReferenceRange(data.get(1));
            order.setvCompound1Concentration(data.get(2));
            order.setvCompound1Comments(data.get(3));
        }
    }

    private void setVCompound2(final String stringFromReport, Order order) {
        String vCompound2 = "VCompound2 ";

        if (stringFromReport.contains(vCompound2)) {
            String tempStringFromReport = stringFromReport.replace(vCompound2, "");

            List<String> data = new ArrayList<>();
            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(" ")).toArray(String[]::new));

            //I compound two last words here, because I separated those two words in previous line.
            StringBuilder result = new StringBuilder(data.get(data.size() - 2) + " " + data.get(data.size() - 1));
            data.remove(data.size() - 1);
            data.remove(data.size() - 1);
            data.add(result.toString());

            //I'm checking for comparison sign. If we have comparison sign than I compound it with value.
            if (data.size() > 4) {
                StringBuilder twoString = new StringBuilder(data.get(1) + " " + data.get(2));
                data.remove(1);
                data.remove(1);
                data.add(1, twoString.toString());
            }

            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            order.setvCompound2Result(data.get(0));
            order.setvCompound2ReferenceRange(data.get(1));
            order.setvCompound2Concentration(data.get(2));
            order.setvCompound2Comments(data.get(3));
        }
    }

    private void setCompound1(final String stringFromReport, Order order) {
        String compound1 = "Compound1 ";

        if (stringFromReport.contains(compound1)) {
            String tempStringFromReport = stringFromReport.replace(compound1, "");

            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            System.out.println(data.size());
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            order.setCompound1Result(data.get(0));
            order.setCompound1Cutoff(data.get(1));
            order.setCompound1Concentration(data.get(2));
            order.setCompound1Comments(data.get(3));
        }
    }

    public void setCompound2(final String stringFromReport, Order order) {
        String compound1 = "Compound2 ";

        if (stringFromReport.contains(compound1)) {
            String tempStringFromReport = stringFromReport.replace(compound1, "");

            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            System.out.println(data.size());
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            order.setCompound2Result(data.get(0));
            order.setCompound2Cutoff(data.get(1));
            order.setCompound2Concentration(data.get(2));
            order.setCompound2Comments(data.get(3));
        }
    }

    private void setPatientNameAndPhysician(final String stringFromReport, Order order) {
        String patient = "Patient Name";
        String physician = "Physician";
        if (stringFromReport.contains(patient) && stringFromReport.contains(physician)) {
            String tempStringFromReport = stringFromReport.replace("Patient Name", "").replace("Physician", "");
            List<String> data = Arrays.asList(tempStringFromReport.split(":"));

            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            order.setPatientName(data.get(1));
            order.setPhysician(data.get(2));
        }
    }

    private void setAccessionNumber(final String stringFromReport, Order order) {
        String accession = "Accession";

        if (stringFromReport.contains(accession)) {
            List<String> data = Arrays.asList(stringFromReport.split(":"));
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }
            order.setAccessionNumber(data.get(1));
        }

    }

    private void setPatientDOBAndSampleType(final String stringFromReport, Order order) {
        String patientDOB = "Patient DOB: ";
        String sampleType = "Sample Type : ";

        if (stringFromReport.contains(patientDOB) && stringFromReport.contains(sampleType)) {
            String tempStringFromReport = stringFromReport.replace(patientDOB, "").replace(sampleType, "");

            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            order.setPatientDOB(data.get(0));
            order.setSampleType(data.get(1));
        }
    }

    private void setCollectedDateAndReceivedInLab(final String stringFromReport, Order order) {
        String collectDate = "Collected Date : ";
        String receivedInLab = "Received in Lab: ";

        if (stringFromReport.contains(collectDate) && stringFromReport.contains(receivedInLab)) {
            String tempStringFromReport = stringFromReport.replace(collectDate, "").replace(receivedInLab, "");

            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            order.setCollectDate(data.get(0));
            order.setReceivedInLab(data.get(1));
        }
    }

    private Order getOrderFromWeb(String accessionNumber) {
        Order order = new Order();

        try {
            driver.findElement(By.cssSelector(WElement.SEARCH_ORDER_FIELD)).clear();
            driver.findElement(By.cssSelector(WElement.SEARCH_ORDER_FIELD)).sendKeys(accessionNumber);
            Thread.sleep(2000);
            driver.findElement(By.id("editlink")).click();
            Thread.sleep(2000);


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
            ;
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
            ;
            String vCompound2Concentration = driver
                    .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(8) > div > div:nth-child(4) > div > input"))
                    .getAttribute("value");
            String vCompound2Comments = driver
                    .findElement(By.cssSelector("#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(8) > div > div:nth-child(5) > div > div.col-sm-9 > div > input"))
                    .getAttribute("value");

            List<String> drugs = driver.findElements(By.className("select2-selection__choice")).stream().map(e -> e.getAttribute("title")).collect(Collectors.toList());

            order.setPatientName(patientName);
            order.setPatientDOB(patientDOB);
            order.setAccessionNumber(accessionNumber);
            order.setCollectDate(dateCollected);
            order.setPhysician(physician);
            order.setSampleType(sampleType);
            order.setReceivedInLab(dateReceived);

            order.setCompound1Result(compound1Result);
            order.setCompound1Cutoff(compound1Cutoff);
            order.setCompound1Concentration(compound1Concentration);
            order.setCompound1Comments(compound1Comments);

            order.setCompound2Result(compound2Result);
            order.setCompound2Cutoff(compound2Cutoff);
            order.setCompound2Concentration(compound2Concentration);
            order.setCompound2Comments(compound2Comments);

            order.setvCompound1Result(vCompound1Result);
            order.setvCompound1ReferenceRange(vCompound1ReferenceRange);
            order.setvCompound1Concentration(vCompound1Concentration);
            order.setvCompound1Comments(vCompound1Comments);

            order.setvCompound2Result(vCompound2Result);
            order.setvCompound2ReferenceRange(vCompound2ReferenceRange);
            order.setvCompound2Concentration(vCompound2Concentration);
            order.setvCompound2Comments(vCompound2Comments);

            order.setMedications(drugs);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return order;
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

    public String getContentFromReport(PDDocument report) {
        try {
            PDFTextStripper printer = new PDFTextStripper();
            printer.setSortByPosition(true);

            return printer.getText(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        options.addArguments("test-type");


        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability("chrome.binary", "./drivers/chromedriver.exe");
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
    }
}
