package com.easytox.automation.steps.lab_pathologist.ET_004;

/**
 * I hold here web elements for test -> LabPathologist ET_004_Pathologist_Number of records per page (use in NumberOfRecordsPerPageSteps.java)
 **/
interface WElement {
    String loginPageFieldName = "j_username";
    String loginPagePasswordField = "j_password";
    String loginPageLoginButton = "button.btn.btn-md.btn-primary";

    String pageHeaderTitle = "li.active:nth-child(3)";

    String dropdownToggle = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(8) > a > i";
    String menuIconLabPathologist = "#topmenu > li:nth-child(9) > a";

    String pathologistPerPage = "#example_length > label > select";
    String oneRowInMainTable = "#example > tbody > tr";

    String amountOfPathologists = "#example_info";
}
