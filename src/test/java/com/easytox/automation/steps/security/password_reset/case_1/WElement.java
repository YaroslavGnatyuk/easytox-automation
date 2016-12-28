package com.easytox.automation.steps.security.password_reset.case_1;

/**
 * I hold here web elements for test -> Security framework test scenarios (use in ResetPasswordPageSteps.java)
 **/

interface WElement {
    String panelFooter = "body > div > div > div > div > div.panel-footer";
    String panelHeading = "body > div > div > div > div > div.panel-heading";
    String bottomClickHere = "body > div > div > div > div > div.panel-footer > a";

    String forgotText = "body > div > div.row > div > div > div > div > div > p";
    String forgotUsernameField = "#actionRequestBy";
    String forgotEmailField = "#actionRequestEmail";
}
