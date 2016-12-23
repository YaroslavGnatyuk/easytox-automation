package com.easytox.automation.steps.lab_pathologist.ET_002;

/**
 * I hold here web elements for test -> LabPathologist ET_002_LabPathologistUpdate (use in LabPathologistUpdateSteps.java)
 **/

interface WElement {
    String loginPageFieldName = "j_username";
    String loginPagePasswordField = "j_password";
    String loginPageLoginButton = "button.btn.btn-md.btn-primary";

    String pageHeaderTitle = "li.active:nth-child(3)";

    String dropdownToggle = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(8) > a > i";
    String menuIconLabPathologist = "#topmenu > li:nth-child(9) > a";

    String pathologistEditIcon = "#editlink > i";

    String pathologistUpdatePageUserNameField = "#form > div:nth-child(3) > div:nth-child(1) > div > span > input";
    String pathologistUpdatePagePasswordField = "#form > div:nth-child(3) > div:nth-child(2) > div > span > input";
    String pathologistUpdatePageFirstNameField = "#form > div:nth-child(5) > div:nth-child(1) > div > span > input";
    String pathologistUpdatePageLastNameField = "#form > div:nth-child(5) > div:nth-child(2) > div > span > input";
    String pathologistUpdatePagePhoneNumberField = "#form > div:nth-child(6) > div > div > span > input";
    String pathologistUpdatePageEmailField = "#form > div:nth-child(6) > div:nth-child(2) > div > span > input";
    String pathologistUpdatePageSalutationField = "#form > div:nth-child(8) > div:nth-child(1) > div > span > input";
    String pathologistUpdatePageMedicareNumberField = "#form > div:nth-child(8) > div:nth-child(2) > div > span > input";
    String pathologistUpdatePageMedicaidNumberField = "#form > div:nth-child(9) > div:nth-child(1) > div > span > input";
    String pathologistUpdatePageUPINNumberField = "#form > div:nth-child(9) > div:nth-child(2) > div > span > input";
    String pathologistUpdatePageStateLicenceField = "#form > div:nth-child(10) > div:nth-child(1) > div > span > input";
    String pathologistUpdatePageNPIField = "#form > div:nth-child(10) > div:nth-child(2) > div > span > input";
    String pathologistUpdatePageAddSubmit = "#form > div:nth-child(11) > div > div > div > button";

    String updatePathologistSuccessTitle = "#maincontentdiv > div.page-body > div.alert.alert-success.fade.in > strong";
}
