package com.easytox.automation.steps.security.password_reset.case_4;

import com.easytox.automation.driver.DriverBase;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class PasswordResetLabPathologist {
    private WebDriver driver;
    private Logger log = Logger.getLogger(PasswordResetLabPathologist.class);
    private static final String easyToxLoginPage = "http://bmtechsol.com:8080/easytox/";
    private static final String currentLabPage = "http://bmtechsol.com:8080/easytox/caseOrder/list"; //This address we have after successful logging
    private static final String forgotPasswordPage = "http://bmtechsol.com:8080/easytox/actionItem/forgotPassword";
    private static final String resetPasswordPage = "http://bmtechsol.com:8080/easytox/actionItem/resetPassword";
    private static final String validEmailAddress = "someEmail@gmail.com";

    private String dateForSearch = null;
    private WebElement allLabClientData = null;

    @Before
    public void init() {
        DriverBase.instantiateDriverObject();
        driver = DriverBase.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @When("^Launch application using link: \"([^\"]*)\"$")
    public void goToLoginPage(String address) {
        driver.navigate().to(address);
    }

    @Then("^Easytox Login screen should be displayed.$")
    public void checkCurrentScreen() {
        String headerTextShouldBe = "Sign In";
        String headerTextWeHave = driver.findElement(By.cssSelector(WElement.panelHeading)).getText();
//        log.info(headerTextShouldBe + "\n");
//        log.info(headerTextWeHave + "\n");
        assertTrue(headerTextWeHave.equals(headerTextShouldBe));
    }

    @When("^Click \"Click here\" link on Forgot Password.$")
    public void gotToForgotPasswordPage() {
        driver.navigate().to(easyToxLoginPage);
        driver.findElement(By.linkText("Click here")).click();
    }

    @Then("^Forgot Password screen should be displayed.$")
    public void checkForgotPasswordPage(){
        String currentPage = driver.getCurrentUrl();
        assertTrue(currentPage.equals(forgotPasswordPage));
    }

    @When("^Enter Username as \"([^\"]*)\", valid email address and click \"Send my Password\" button.$")
    public void sendRequestForRecoveryPassword(String validUsername){
        driver.findElement(By.cssSelector(WElement.forgotPageUsernameField)).sendKeys(validUsername);
        driver.findElement(By.cssSelector(WElement.forgotPageEmailField)).sendKeys(validEmailAddress);
        driver.findElement(By.cssSelector(WElement.forgotPageSendMyPasswordButton)).click();
    }

    @Then("^\"Forgot Password Request Submitted\" message should be displayed on the page.$")
    public void checkTheMessage(){
        String messageShouldBe = "Forgot Password request submitted";
        String messageWeHave = driver
                .findElement(By.cssSelector(WElement.loginPage_MessageAfterRequestPasswordRecovery))
                .getText();

        assertTrue(messageWeHave.equals(messageShouldBe));
    }

    @When("^Login to Easytox with \"([^\"]*)\" and \"([^\"]*)\" credentials.$")
    public void loginToEasyTox(String username, String password){
        driver.navigate().to(easyToxLoginPage);
        driver.findElement(By.name(WElement.loginPage_fieldName)).sendKeys(username);
        driver.findElement(By.name(WElement.loginPage_passwordField)).sendKeys(password);
        driver.findElement(By.cssSelector(WElement.loginPage_loginButton)).click();
    }

    @Then("^User login should be successful.$")
    public void checkCurrentPage(){
        try {
            Thread.sleep(1000);
            assertTrue(driver.getCurrentUrl().equals(currentLabPage));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Click \"Pending Password Requests\" link.$")
    public void clickOnThePendingPasswordRequest(){
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.pendingPasswordRequest)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Then("^Following should be displayed:1 Password Requests$")
    public void isPasswordRequestShowed(){
        boolean isPasswordRequestsShowed = driver
                .findElement(By.cssSelector(WElement.passwordRequest))
                .isDisplayed();
        assertTrue(isPasswordRequestsShowed);
    }
    @And("^Forgot Password <link>$")
    public void isForgotPasswordShowed(){
        String request = "ForgotPassword SPathone";
        List<WebElement> li = driver.findElements(By.cssSelector(WElement.userRequestLine));
        assertTrue(li.size() > 0);
    }
    @And("^See All Requests <Link>$")
    public void isAllRequestLinkShowed(){
        WebElement textLink = driver.findElement(By.linkText(WElement.textLinkSeeAllRequest));
        assertNotNull(textLink);
    }

    @When("^Select \"See All Requests\".$")
    public void selectAllRequest(){
        driver.findElement(By.linkText(WElement.textLinkSeeAllRequest)).click();
    }

    @Then("^Password Request List\" screen should be displayed.$")
    public void isPasswordRequestListShowed(){
        try {
            Thread.sleep(1000);
            String tableNameShouldBe = "Request List";
            String tableNameWeHave = driver.findElement(By.cssSelector(WElement.tableName)).getText();

//            log.info(tableNameWeHave + "\n");
//            log.info(tableNameShouldBe + "\n");

            assertTrue(tableNameWeHave.equals(tableNameShouldBe));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Verify the details displayed for password reset request of \"([^\"]*)\".$")
    public void findClient(String labClient){
        allLabClientData = getLabClientWithStatusPending(labClient);
        assertTrue(allLabClientData != null);
    }

    @Then("^Following values should be displayed against password reset request : Request -> \"([^\"]*)\"$")
    public void isTypeOfRequestShowed(String requestType){
        String requestTypeWeHave = allLabClientData.findElement(By.cssSelector(WElement.mainTable_request)).getText();

        assertTrue(requestTypeWeHave.equals(requestType));
    }

    @And("^Requested by -> \"([^\"]*)\"$")
    public void IsRequestBy(String username){
        String usernameWeHave = allLabClientData.findElement(By.cssSelector(WElement.mainTable_requestedBy)).getText();
        assertTrue(usernameWeHave.equals(username));
    }

    @And("^Requested Date -> valid date in the format \"([^\"]*)\"$")
    public void isDateValid(String dateFormat){
        String dateWeHave = dateForSearch = allLabClientData.findElement(By.cssSelector(WElement.mainTable_requestedDate)).getText();

        boolean isDateEquals = false;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        try {
            Date date = format.parse(dateWeHave);
            if(dateWeHave.equals(format.format(date))){
                isDateEquals = true;
            }

            assertTrue(isDateEquals);
        } catch (ParseException e ) {
            e.printStackTrace();
        }
    }

    @And("^Requested email -> Entered email ID in Step 3 above.$")
    public void isEmailValid(){
        String emailWeHave = allLabClientData.findElement(By.cssSelector(WElement.mainTable_requestedEmail)).getText();
        assertTrue(emailWeHave.equals(validEmailAddress));
    }

    @And("^Status -> \"([^\"]*)\"$")
    public void checkTheStatus(String status){
        String statusWeHave = allLabClientData.findElement(By.cssSelector(WElement.mainTable_status)).getText();
        assertTrue(statusWeHave.equals(status));
    }

    @And("^Action -> Edit icon.$")
    public void checkEditIcon(){
        boolean isEditIconShowed = allLabClientData
                .findElement(By.cssSelector(WElement.mainTable_editIcon))
                .isDisplayed();
        assertTrue(isEditIconShowed);
    }

    @When("^Click on Edit icon against SPathOne request.$")
    public void clickOnEditIcon(){
        driver.findElement(By.cssSelector(WElement.mainTable_editIcon)).click();
    }

    @Then("^Reset Password screen should be displayed.$")
    public void isResetPasswordScreenShowed(){
        try {
            Thread.sleep(1000);
            assertTrue(driver.getCurrentUrl().equals(resetPasswordPage));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter a new password \"([^\"]*)\" in the \"New Password\" field and click Save.$")
    public void enterNewPassword(String newPassword) {
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.resetPasswordPage_newPasswordField)).sendKeys(newPassword);
            driver.findElement(By.cssSelector(WElement.resetPasswordPage_saveButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^\"([^\"]*)\" message should be displayed.$")
    public void checkTheResultOfSettingNewPassword(String messageShouldBe) {
        try {
            Thread.sleep(1000);
            String messageWeHave = driver
                    .findElement(By.cssSelector(WElement.resultOfChangePassword))
                    .getText();
            log.info(messageWeHave + "\n");
            log.info(messageShouldBe + "\n");
            assertTrue(messageWeHave.equals(messageShouldBe));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @And("^Status of the request should be changed to \"([^\"]*)\".$")
    public void checkStatusOfRequest(String status){
        try {
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.pendingPasswordRequest)).click();
            Thread.sleep(300);
            driver.findElement(By.linkText(WElement.textLinkSeeAllRequest)).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.searchField)).sendKeys(dateForSearch);
            String statusWeHave = driver
                    .findElement(By.cssSelector(WElement.mainTable_status))
                    .getText();
            assertTrue(statusWeHave.equals(status));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Login to the Easytox with \"([^\"]*)\" and \"([^\"]*)\" credentials.$")
    public void loginIntoEasyTox(String login, String password){
        try {
            signOut();
            Thread.sleep(1000);
            driver.findElement(By.name(WElement.loginPage_fieldName)).sendKeys(login);
            driver.findElement(By.name(WElement.loginPage_passwordField)).sendKeys(password);
            driver.findElement(By.cssSelector(WElement.loginPage_loginButton)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^\"Change Password\" screen with following fields should be displayed : Username$")
    public void isUsernameFieldShowed(){
        boolean isUsernameFieldShowed = driver
                .findElement(By.cssSelector(WElement.changePasswordPage_usernameField))
                .isDisplayed();
        assertTrue(isUsernameFieldShowed);
    }

    @And("^Old Password$")
    public void isOldPasswordFieldShowed(){
        boolean isOldPasswordFieldShowed = driver
                .findElement(By.cssSelector(WElement.changePasswordPage_oldPasswordField))
                .isDisplayed();
        assertTrue(isOldPasswordFieldShowed);
    }
    @And("^New Password$")
    public void isNewPasswordFieldShowed(){
        boolean isNewPasswordFieldShowed = driver
                .findElement(By.cssSelector(WElement.changePasswordPage_newPasswordField))
                .isDisplayed();
        assertTrue(isNewPasswordFieldShowed);
    }
    @And("^Confirm Password$")
    public void isConfirmPasswordFieldShowed(){
        boolean isConfirmPasswordFieldShowed = driver
                .findElement(By.cssSelector(WElement.changePasswordPage_confirmPasswordField))
                .isDisplayed();
        assertTrue(isConfirmPasswordFieldShowed);
    }

    @When("^Verify username \"([^\"]*)\" populated in \"Username\" field.$")
    public void checkUsernameInUsernameField(String autoPopulatedUsername){
        try {
            Thread.sleep(1000);

            String autoPopulatedUsernameWeHave = driver
                    .findElement(By.cssSelector(WElement.changePasswordPage_usernameField))
                    .getAttribute("value");
//            log.info(autoPopulatedUsername + "\n");
//            log.info(autoPopulatedUsernameWeHave + "\n");
            assertTrue(autoPopulatedUsername.equals(autoPopulatedUsernameWeHave));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^\"SPathOne\" should be autopopulated in \"username\" field and it should NOT be editable.$")
    public void isUsernameFieldEditable(){
        String editable = driver
                .findElement(By.cssSelector(WElement.changePasswordPage_usernameField))
                .getAttribute("readonly");
        assertNotNull(editable);
    }

    @When("^Enter \"([^\"]*)\" in the Old Password field.$")
    public void enterOldPassword(String oldPassword){
        driver.findElement(By.cssSelector(WElement.changePasswordPage_oldPasswordField)).sendKeys(oldPassword);
    }

    @Then("^User old password should be successful.$")
    public void checkOldPassword(){
        String oldPassword = driver
                .findElement(By.cssSelector(WElement.changePasswordPage_oldPasswordField))
                .getAttribute("value");
        assertNotNull(oldPassword);
    }

    @When("^Enter \"([^\"]*)\" in the New Password field.$")
    public void enterPassword(String newPassword){
        driver.findElement(By.cssSelector(WElement.changePasswordPage_newPasswordField)).sendKeys(newPassword);
    }

    @Then("^User new password should be successful.$")
    public void checkNewPassword(){
        String newPassword = driver
                .findElement(By.cssSelector(WElement.changePasswordPage_newPasswordField))
                .getAttribute("value");
        assertNotNull(newPassword);
    }

    @When("^Enter \"([^\"]*)\" in the Confirm Password field.$")
    public void enterConfirmPassword(String confirmPassword){
        driver.findElement(By.cssSelector(WElement.changePasswordPage_confirmPasswordField)).sendKeys(confirmPassword);
    }

    @Then("^User entry should be successful.$")
    public void checkConfirmPassword(){
        String confirmPassword = driver
                .findElement(By.cssSelector(WElement.changePasswordPage_confirmPasswordField))
                .getAttribute("value");
        assertNotNull(confirmPassword);
    }

    @When("^Click save.$")
    public void clickSave(){
        driver.findElement(By.cssSelector(WElement.changePasswordPage_saveButton)).click();
    }

    @Then("^Login screen with message \"([^\"]*)\" should be displayed.$")
    public void checkShowedMessage(String messageShouldBeDisplayed){
        try {
            Thread.sleep(1000);

            String theMessageWeHave = driver
                    .findElement(By.cssSelector(WElement.loginPage_passwordChangedSuccessfullyMessage))
                    .getText();
            assertTrue(theMessageWeHave.equals(messageShouldBeDisplayed));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^Enter \"([^\"]*)\" and \"([^\"]*)\" as user credentials and login.$")
    public void enterNewUserCredentials(String login, String password){
        driver.findElement(By.name(WElement.loginPage_fieldName)).clear();
        driver.findElement(By.name(WElement.loginPage_fieldName)).sendKeys(login);
        driver.findElement(By.name(WElement.loginPage_passwordField)).clear();
        driver.findElement(By.name(WElement.loginPage_passwordField)).sendKeys(password);
        driver.findElement(By.cssSelector(WElement.loginPage_loginButton)).click();
    }

    @Then("^User login should be successful with reset password.$")
    public void checkUserLogging(){
        try {
            Thread.sleep(1000);
            String thePageShouldBe = "http://bmtechsol.com:8080/easytox/caseOrder/list";
            String thePageWeHave = driver.getCurrentUrl();

            assertTrue(thePageWeHave.equals(thePageShouldBe));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close(){driver.close();}

    private WebElement getLabClientWithStatusPending(String labClient) {
        List<WebElement> allResults ;

        try {
            Thread.sleep(300);
            driver.findElement(By.cssSelector(WElement.pendingPasswordRequest)).click();
            Thread.sleep(300);
            driver.findElement(By.linkText(WElement.textLinkSeeAllRequest)).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(WElement.searchField)).sendKeys(labClient);
            Thread.sleep(100);
            new Select(driver.findElement(By.cssSelector(WElement.itemPerPage))).selectByVisibleText("All");
            allResults = driver.findElements(By.cssSelector(WElement.allResultOfSearch));
            Thread.sleep(1000);

            for (int i = 0; i < allResults.size(); i++) {
                boolean isPendingRequest = allResults.get(i)
                        .findElement(By.cssSelector("#example > tbody > tr:nth-child(" + (i + 1) + ") > td:nth-child(6)"))
                        .getText()
                        .equals("Pending");
                if (isPendingRequest) {
                    return allResults.get(i);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void signOut(){
        try {
            Thread.sleep(500);
            driver.findElement(By.cssSelector(WElement.loginDropDown)).click();
            Thread.sleep(500);
            driver.findElement(By.cssSelector(WElement.signOutField)).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
