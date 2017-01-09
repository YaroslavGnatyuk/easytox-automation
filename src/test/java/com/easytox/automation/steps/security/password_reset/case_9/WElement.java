package com.easytox.automation.steps.security.password_reset.case_9;

/**
 * I hold here web elements for test -> LabPathologist ET_006_Pathologist_Page navigation(use in VerifyTheSearchResultSteps.java)
 **/
interface WElement {
    String panelHeading = "body > div > div > div > div > div.panel-heading";
    String tableName = "#maincontentdiv > div.page-body > div > div > div > div.widget-header > span";

    String loginPage_fieldName = "j_username";
    String loginPage_passwordField = "j_password";
    String loginPage_loginButton = "button.btn.btn-md.btn-primary";

    String pendingPasswordRequest = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(6) > a";
    String passwordRequest = "#pendingpasswordrequests > li.dropdown-header.bordered-darkorange";
    String userRequestLine = "#pendingpasswordrequests > li";

    String pageHeaderTitle = "li.active:nth-child(3)";

    String dropdownToggle = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(8) > a > i";
    String menuIconLabPathologist = "#topmenu > li:nth-child(9) > a";

    String mainSearchField = "#example_filter > label > input";
    String resultSearching = "#example > tbody > tr:nth-child(1)";
    String oneRowInResultSearching = "#example > tbody > tr > td";
    String textLinkSeeAllRequest = "See All Requests";
}
