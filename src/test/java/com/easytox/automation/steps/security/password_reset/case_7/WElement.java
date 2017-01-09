package com.easytox.automation.steps.security.password_reset.case_7;

/**
 * I hold here web elements for test -> Security framework test scenarios (use in VerifyDataSortingSteps.java)
 **/

interface WElement {
    String panelHeading = "body > div > div > div > div > div.panel-heading";
    String amountOfRequest = "#example_info";
    String tableName = "#maincontentdiv > div.page-body > div > div > div > div.widget-header > span";
    String requestPerPage = "#example_length > label > select";

    String columnsName = "#example > thead > tr > th";

    String loginPage_fieldName = "j_username";
    String loginPage_passwordField = "j_password";
    String loginPage_loginButton = "button.btn.btn-md.btn-primary";

    String pendingPasswordRequest = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(6) > a";
    String passwordRequest = "#pendingpasswordrequests > li.dropdown-header.bordered-darkorange";
    String userRequestLine = "#pendingpasswordrequests > li";

    String textLinkSeeAllRequest = "See All Requests";
}
