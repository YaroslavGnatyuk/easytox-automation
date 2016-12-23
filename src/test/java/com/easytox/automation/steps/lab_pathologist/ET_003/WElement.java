package com.easytox.automation.steps.lab_pathologist.ET_003;

/**
 * I hold here web elements for test -> LabPathologist ET_003_Pathologist_Search Lab Pathologist (use in SearchLabPathologistSteps.java)
 **/
interface WElement {
    String loginPageFieldName = "j_username";
    String loginPagePasswordField = "j_password";
    String loginPageLoginButton = "button.btn.btn-md.btn-primary";

    String pageHeaderTitle = "li.active:nth-child(3)";

    String dropdownToggle = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(8) > a > i";
    String menuIconLabPathologist = "#topmenu > li:nth-child(9) > a";

    String mainSearchField = "#example_filter > label > input";
    String resultSearching = "#example > tbody > tr:nth-child(1)";
    String oneRowInResultSearching = "#example > tbody > tr > td";
}
