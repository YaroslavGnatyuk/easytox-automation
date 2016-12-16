package com.easytox.automation.steps.security.search.cooper;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.AssertJUnit.assertEquals;

public class SearchForCooperPatientsSteps {
    private WebDriver driver;
    private static final String easytoxAddress = "http://bmtechsol.com:8080/easytox/";
    private static final String patientsPageAddress = "http://bmtechsol.com:8080/easytox/patient/patientlist";

    private List<String> questPatients;
    private List<String> westPatients;
    private List<String> zestPatients;

    private int patientsFromOtherLab = 0;
    private int patientsNotFoundFromZestLab = 0;

    private Logger log = Logger.getLogger(SearchForCooperPatientsSteps.class);
    private List<String> junkData;

    @Before
    public void gotoEasytoxAutomation() {
        initPatients();

        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();

        driver.navigate().to(easytoxAddress);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^User login with the physician \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void login(String usr, String psw) {
        try {
            driver.findElement(By.name("j_username")).sendKeys(usr);
            driver.findElement(By.name("j_password")).sendKeys(psw);
            driver.findElement(By.cssSelector("button.btn.btn-md.btn-primary")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("^User should be able to go to Physician page$")
    public void isItAbleToGoToPhysicianPage() {
        try {
            Thread.sleep(1500);
            driver.findElement(By.cssSelector(".account-area > li:nth-child(3) > a:nth-child(1)")).click();
            boolean isDisplayedPhysician = driver.findElement(By.cssSelector(".open > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)")).isDisplayed();

            assertEquals("Case should be able to go to Physician page", true, isDisplayedPhysician);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^User clicks on Patient from menu$")
    public void gotoPatientPage() {
        driver.findElement(By.cssSelector(".open > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)")).click();
    }

    @Then("^User should be navigated to Patients list page$")
    public void isItPatientsPage() {
        String patientsPageAddressFromDriver = driver.getCurrentUrl();

        assertEquals("Addresses must be equals", true,
                patientsPageAddress.equalsIgnoreCase(patientsPageAddressFromDriver));
    }

    @When("^Check for the patients list$")
    public void openPatientList() {
        driver.navigate().to(patientsPageAddress);
    }

    @Then("^All patients from Zest lab should be displayed$")
    public void checkDisplayedPatients() {
        try {
            new Select(driver.findElement(By.name("example_length"))).selectByVisibleText("All");

            Thread.sleep(1000);

            Set<String> allPatients = new HashSet<>();
            List<WebElement> elementsOdd = driver.findElements(By.cssSelector("tr.odd"));
            List<WebElement> elementsEven = driver.findElements(By.cssSelector("tr.even"));

            for (int i = 0; i < elementsOdd.size(); i++) {
                allPatients.add(elementsOdd.get(i).findElements(By.cssSelector("td")).get(1).getText());
            }

            for (int i = 0; i < elementsEven.size(); i++) {
                allPatients.add(elementsEven.get(i).findElements(By.cssSelector("td")).get(1).getText());
            }

            assertEquals("Patients from zest lab should be in list"
                    , true, allPatients.containsAll(zestPatients));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Check for the other patients$")
    public void reopenPatientList() {
        driver.navigate().to(patientsPageAddress);
    }

    @Then("^There should be only patients from ZestLab$")
    public void checkPatientsList() {
        try {
            new Select(driver.findElement(By.name("example_length"))).selectByVisibleText("All"); //I choose "All" patients on one list

            Thread.sleep(1000);

            Set<String> allPatients = new HashSet<>();

            List<WebElement> elementsOdd = driver.findElements(By.cssSelector("tr.odd"));    //I get all patients name from line .odd
            List<WebElement> elementsEven = driver.findElements(By.cssSelector("tr.even"));

            for (int i = 0; i < elementsOdd.size(); i++) {
                allPatients.add(elementsOdd.get(i).findElements(By.cssSelector("td")).get(1).getText());
            }

            for (int i = 0; i < elementsEven.size(); i++) {
                allPatients.add(elementsEven.get(i).findElements(By.cssSelector("td")).get(1).getText());
            }

            allPatients.removeAll(junkData); //filter for junk data
            allPatients.removeAll(zestPatients);

           /* if (!allPatients.isEmpty()) {
                throw new PatientSearchException("There should be only patients from ZestLab \n" +
                        "These patients shouldn't be in the list:\n" +
                        allPatients.stream().collect(Collectors.joining(",\n")));
            }*/
            assertEquals(allPatients.stream().collect(Collectors.joining(",")),
                    true, allPatients.isEmpty());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^User search for the patients which are in Zest Lab Client list$")
    public void searchExistPatients() {
        for (String patient : zestPatients) {
            driver.findElement(By.cssSelector("input.input-sm")).sendKeys(patient);

            try {

                driver.findElement(By.cssSelector(".odd > td:nth-child(2)")).getText(); //If I found this element (field with name of patient) it means
                Thread.sleep(300);    // that patient was found, else we have exception NoSuchElementException
                driver.findElement(By.cssSelector("input.input-sm")).clear();

            } catch (NoSuchElementException e) {            //If we have this exception it means that patient wasn't found
                patientsNotFoundFromZestLab++;
                log.info("Patient not found: " + patient);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Then("^Results should be displayed as for the search made$")
    public void resultSearch(){
        assertEquals("All user should be to found, so 'patientsNotFoundFromWestLab' should be to equal zero",
                true, patientsNotFoundFromZestLab == 0);
    }

    @When("^User search for the patients from other lab$")
    public void searchPatientFromOtherLab() {
        search(questPatients);
        search(westPatients);
    }

    @Then("^No results should be displayed when searched with the patients other than Zest Lab Client Patients$")
    public void resultSearchForPatientsFromOtherLabs(){
        assertEquals("No one user should be to found from other labs, so 'patientsFromOtherLab' shouldn't be",
                true, patientsFromOtherLab == 0);
    }

    public void search(List<String> patients){
        for (String patient : patients) {
            driver.findElement(By.cssSelector("input.input-sm")).sendKeys(patient);

            try {

                driver.findElement(By.cssSelector(".dataTables_empty")).getText(); //If I found this element it means
                Thread.sleep(300);    // that patient not found, else we have exception NoSuchElementException
                driver.findElement(By.cssSelector("input.input-sm")).clear();

            } catch (NoSuchElementException e) {            //If we have this exception it means that patient was found
                if(!junkData.contains(patient)){            //filter for junk data
                    patientsFromOtherLab++;
                    log.info("Patient found from other lab: " + patient);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void afterAll() {
        driver.close();
    }

    private void initPatients() {
        questPatients = Stream.of(new String[]{"Raja Raman", "Red Vandit", "Riley William", "Ronnie Hebrew",
                "Patrik Latin", "Paige Wandel", "Poppy Snider", "Penny Greek"
                , "Sally Debri", "Sebastin Tink", "Sienna Avany", "Summer Junk",
                "Quina Samer", "Qiang Tiny", "Quang Mint", "Qillq Brad"
        }).collect(Collectors.toList());

        westPatients = Stream.of(new String[]{"Emma Girl", "Erica Falen", "Ethen Hebrew", "Edward Joseph",
                "Franky Italy", "Farrah Sara", "Florence King", "Finley Celtic",
                "Gracie Emma", "Greger Welsh", "Gabriel Dash", "Gabby Unite",
                "Henry Olidy", "Harvey Utah", "Holley Hoger", "Heidi Zaby"
        }).collect(Collectors.toList());

        zestPatients = Stream.of(new String[]{
                "Jayden Grack", "Julia Potter", "Jacob Penny", "James Vict",
                "Kayal Sally", "Kaiden Wand", "Keira Sink", "Krishna Olive",
                "Logan Matt", "Levin Tirey", "Lewis Port", "Lacey Waste",
                "Mirchi Kite", "Mason ferg", "Megan Force", "Maisie Xavior"
        }).collect(Collectors.toList());

        junkData = Stream.of(new String[]{
                "PtB QABBatman","PtBbb QABBatman","Raja Raman lastName","PtBb QABBatman",
                "PtA Q QABant","PtC QACatman","PtCccc QACatman","PtCcc QACatman",
                "Red vandit lastName","Ronnie Hebrew lastName","PtAaa QABAnt","PtCc QACatman",
                "Riley william lastName","PtBbbb QABBatman","PtAa QABAnt","PtAaaa QABAnt",
                "Raja Raman","madhu madhu"
        }).collect(Collectors.toList());
    }
}
