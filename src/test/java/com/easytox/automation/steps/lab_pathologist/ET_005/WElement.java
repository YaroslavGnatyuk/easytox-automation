package com.easytox.automation.steps.lab_pathologist.ET_005;

/**
 * I hold here web elements for test -> LabPathologist ET_005_Pathologist_Sorting data(use in SortingDataSteps.java)
 **/

interface WElement {
    String loginPageFieldName = "j_username";
    String loginPagePasswordField = "j_password";
    String loginPageLoginButton = "button.btn.btn-md.btn-primary";

    String pageHeaderTitle = "li.active:nth-child(3)";

    String dropdownToggle = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(8) > a > i";
    String menuIconLabPathologist = "#topmenu > li:nth-child(9) > a";

    String rowWithColumnsName = "#example > thead > tr";
    String columnsName = "#example > thead > tr > th";
    String amountOfPathologists = "#example_info";

    String pathologistPerPage = "#example_length > label > select";
}
