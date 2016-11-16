package com.easytox.automation.steps;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;

public class AddClientSteps {
    private WebDriver driver;

    private String httpAddress = "http://bmtechsol.com:8080/easytox/";
    private String login = "lavanya1";
    private String password = "P@ssw0rd123";

    @Before
    public void login(){
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();

        driver.get(httpAddress);
        driver.findElement(By.name("j_username")).sendKeys(login);
        driver.findElement(By.name("j_password")).sendKeys(password);
        driver.findElement(By.cssSelector("button.btn.btn-md.btn-primary")).click();
    }

    @When("^I select LabClient from the settings menu$")
    public void openLAbClientPage() {
        try {
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("i.icon.fa.fa-cog")).click();
            driver.findElement(By.cssSelector("#topmenu > li:nth-child(5) > a > img")).click();

        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
        }
    }

    @Then("^LabClient should be open$")
    public void checkOpenClientPage() {
        try {

            String titleFromDriver = driver.getTitle();
            String title = "LabClient List";

            assertEquals(true, titleFromDriver.equals(title), "titleFromDriver should be equal to title");

        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
        }
    }

    @When("^Select icon next to search box$")
    public void openAddClientPage() {

        try {
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#example_wrapper > div.toolbar > a")).click();
        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
        }
    }

    @Then("^Add Lab Client page should be open$")
    public void checkOpenAddClientPage() {
        try {
            String titlePage = "Create LabClient";
            String titlePageFromDriver = driver.getTitle();

            assertEquals(true, titlePage.equals(titlePageFromDriver), "titlePageFromDriver should be equal to titlePage");
        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
        }
    }

    @When("^Enter business name (.*) and enter all the required details and click on Submit$")
    public void addNewClient(String labClient) {
        try {
            LabClient client = getNewClient();
            client.setBusinessName(labClient);

            driver.findElement(By.cssSelector("#form > div:nth-child(2) > div > input"))
                    .sendKeys(client.getBusinessName());

            driver.findElement(By.cssSelector("#form > div:nth-child(3) > div > input"))
                    .sendKeys(client.getAddress1());

            driver.findElement(By.cssSelector("#form > div:nth-child(4) > div > input"))
                    .sendKeys(client.getAddress2());

            driver.findElement(By.cssSelector("#zip"))
                    .sendKeys(String.valueOf(client.getZipCode()));

            driver.findElement(By.cssSelector("#city"))
                    .clear();
            driver.findElement(By.cssSelector("#city"))
                    .sendKeys(client.getCity());

            driver.findElement(By.cssSelector("#state"))
                    .clear();
            driver.findElement(By.cssSelector("#state"))
                    .sendKeys(client.getState());

            driver.findElement(By.cssSelector("#form > div:nth-child(8) > div > input"))
                    .sendKeys(client.getContactPerson());

            driver.findElement(By.cssSelector("#form > div:nth-child(10) > div > input"))
                    .sendKeys(client.getFax());
            driver.findElement(By.cssSelector("#form > div:nth-child(11) > div > input"))
                    .sendKeys(client.getEmail());

            driver.findElement(By.cssSelector("#form > div:nth-child(9) > div > input"))
                    .click();
            driver.findElement(By.cssSelector("#form > div:nth-child(9) > div > input"))
                    .sendKeys(client.getContactNumber());

            driver.findElement(By.cssSelector("#form > div:nth-child(12) > div > button")).click();
        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
        }
    }

    @Then("^Added Lab Client should be displayed in the list$")
    public void checkNewClient() {
        String currentAddress = "http://bmtechsol.com:8080/easytox/labClient/save";
        String currentAddressFromDriver = driver.getCurrentUrl();

        assertEquals(true, currentAddress.equals(currentAddressFromDriver));

        driver.close();
    }

    private LabClient getNewClient() {
        LabClient client = new LabClient();

        client.setAddress1("some address 1");
        client.setAddress2("some address 2");
        client.setZipCode(12345);
        client.setCity("NY");
        client.setState("NY");
        client.setContactPerson("some person 1");
        client.setContactNumber("(333)333-3333");
        client.setFax("1231231230");
        client.setEmail("come@address1.com");

        return client;
    }

}

class LabClient {
    private String businessName;
    private String address1;
    private String address2;
    private long zipCode;
    private String city;
    private String state;
    private String contactPerson;
    private String contactNumber;
    private String fax;
    private String email;

     LabClient() {
     }

     String getBusinessName() {
        return businessName;
    }

     void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

     String getAddress1() {
        return address1;
    }

     void setAddress1(String address1) {
        this.address1 = address1;
    }

     String getAddress2() {
        return address2;
    }

     void setAddress2(String address2) {
        this.address2 = address2;
    }

     long getZipCode() {
        return zipCode;
    }

     void setZipCode(long zipCode) {
        this.zipCode = zipCode;
    }

     String getCity() {
        return city;
    }

     void setCity(String city) {
        this.city = city;
    }

     String getState() {
        return state;
    }

     void setState(String state) {
        this.state = state;
    }

     String getContactPerson() {
        return contactPerson;
    }

     void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

     String getContactNumber() {
        return contactNumber;
    }

     void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

     String getFax() {
        return fax;
    }

     void setFax(String fax) {
        this.fax = fax;
    }

     String getEmail() {
        return email;
    }

     void setEmail(String email) {
        this.email = email;
    }
}