package com.easytox.automation.steps.security.password_reset.case_6;

/**
 * I hold here web elements for test -> Security framework test scenarios (use in ResetPasswordListSteps.java)
 **/
interface WElement {
    String panelHeading = "body > div > div > div > div > div.panel-heading";
    String titleHeader = "#maincontentdiv > div.page-header.position-relative > div.header-title > h1 > small";
    String tableName = "#maincontentdiv > div.page-body > div > div > div > div.widget-header > span";

    String mainTable_requestColumn = "#example > thead > tr > th:nth-child(1)";
    String mainTable_requestByColumn = "#example > thead > tr > th:nth-child(2)";
    String mainTable_requestDateColumn = "#example > thead > tr > th:nth-child(3)";
    String mainTable_requestEmailColumn = "#example > thead > tr > th:nth-child(4)";
    String mainTable_resolvedByColumn = "#example > thead > tr > th:nth-child(5)";
    String mainTable_requestStatusColumn = "#example > thead > tr > th:nth-child(6)";
    String mainTable_requestActionColumn = "#example > thead > tr > th:nth-child(7)";

    String loginPage_fieldName = "j_username";
    String loginPage_passwordField = "j_password";
    String loginPage_loginButton = "button.btn.btn-md.btn-primary";

    String pendingPasswordRequest = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(6) > a";
    String passwordRequest = "#pendingpasswordrequests > li.dropdown-header.bordered-darkorange";
    String userRequestLine = "#pendingpasswordrequests > li";

    String textLinkSeeAllRequest = "See All Requests";
    String searchField = "#example_filter > label > input";
    String firstRowInResultOfSearch = "#example > tbody > tr:nth-child(1)";
}
