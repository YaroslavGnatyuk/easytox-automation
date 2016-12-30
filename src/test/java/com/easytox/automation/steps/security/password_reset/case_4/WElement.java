package com.easytox.automation.steps.security.password_reset.case_4;

/**
 * I hold here web elements for test -> Security framework test scenarios (use in ResetPasswordForLabPathologistSteps.java)
 **/

interface WElement {
    String panelHeading = "body > div > div > div > div > div.panel-heading";
    String searchField = "#example_filter > label > input";

    String forgotPageUsernameField = "#actionRequestBy";
    String forgotPageEmailField = "#actionRequestEmail";
    String forgotPageSendMyPasswordButton = "#form > fieldset > div:nth-child(3) > input";

    String loginPage_MessageAfterRequestPasswordRecovery = "body > div > div > div > div > div.panel-body > div";
    String loginPage_fieldName = "j_username";
    String loginPage_passwordField = "j_password";
    String loginPage_loginButton = "button.btn.btn-md.btn-primary";
    String loginPage_passwordChangedSuccessfullyMessage = "body > div > div > div > div > div.panel-body > div";

    String pendingPasswordRequest = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(6) > a";
    String passwordRequest = "#pendingpasswordrequests > li.dropdown-header.bordered-darkorange";
    String userRequestLine = "#pendingpasswordrequests > li";

    String textLinkSeeAllRequest = "See All Requests";
    String resetPasswordPage_newPasswordField = "#password";
    String resetPasswordPage_saveButton = "#form > fieldset > div:nth-child(4) > div > input";

    String resultOfChangePassword = "#maincontentdiv > div.page-body > div.alert.alert-success.fade.in";
    String loginDropDown = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(7) > a > section > h2 > span";
    String signOutField = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li.open > ul > li.dropdown-footer > a";

    String changePasswordPage_usernameField = "#username";
    String changePasswordPage_oldPasswordField = "#oldPassword";
    String changePasswordPage_newPasswordField = "#password";
    String changePasswordPage_confirmPasswordField = "#password2";
    String changePasswordPage_saveButton = "#changePasswordform > fieldset > div:nth-child(5) > input";
    String itemPerPage = "#example_length > label > select";
    String allResultOfSearch = "#example > tbody > tr";
    String tableName = "#maincontentdiv > div.page-body > div > div > div > div.widget-header > span";

    String mainTable_request = "#example > tbody > tr > td:nth-child(1)";
    String mainTable_requestedBy = "#example > tbody > tr > td:nth-child(2)";
    String mainTable_requestedDate = "#example > tbody > tr > td:nth-child(3)";
    String mainTable_requestedEmail = "#example > tbody > tr > td:nth-child(4)";
    String mainTable_status = "#example > tbody > tr > td:nth-child(6)";
    String mainTable_editIcon = "#editlink > i";
}
