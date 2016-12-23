package com.easytox.automation.steps.lab_pathologist.ET_001;

/**
 * I hold here web elements for test -> LabPathologist ET_001_LabPathologistAdd (use in LabPathologistAddSteps.java)
 **/

interface WElement {
    String loginPageFieldName = "j_username";
    String loginPagePasswordField = "j_password";
    String loginPageLoginButton = "button.btn.btn-md.btn-primary";

    String pageHeaderTitle = "li.active:nth-child(3)";

    String dropdownToggle = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(8) > a > i";
    String menuIconLabPathologist = "#topmenu > li:nth-child(9) > a";

    String createNewPathologistButton = "#example_wrapper > div.toolbar > a > i";

    String pathologistUserNameField = "#form > div:nth-child(3) > div:nth-child(1) > div > span > input";
    String pathologistPasswordField = "#form > div:nth-child(3) > div:nth-child(2) > div > span > input";
    String pathologistFirstNameField = "#form > div:nth-child(5) > div:nth-child(1) > div > span > input";
    String pathologistLastNameField = "#form > div:nth-child(5) > div:nth-child(2) > div > span > input";
    String pathologistPhoneNumberField = "#form > div:nth-child(6) > div > div > span > input";
    String pathologistEmailField = "#form > div:nth-child(7) > div > div > span > input";
    String pathologistSalutationField = "#form > div:nth-child(9) > div:nth-child(1) > div > span > input";
    String pathologistMedicareNumberField = "#form > div:nth-child(9) > div:nth-child(2) > div > span > input";
    String pathologistMedicaidNumberField = "#form > div:nth-child(10) > div:nth-child(1) > div > span > input";
    String pathologistUPINNumberField = "#form > div:nth-child(10) > div:nth-child(2) > div > span > input";
    String pathologistStateLicenceField = "#form > div:nth-child(11) > div:nth-child(1) > div > span > input";
    String pathologistNPIField = "#form > div:nth-child(11) > div:nth-child(2) > div > span > input";
    String pathologistAddSubmit = "#form > div:nth-child(12) > div > div > button";
}
