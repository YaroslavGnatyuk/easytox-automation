package com.easytox.automation.steps.security.password_reset.case_8;

/**
 * I hold here web elements for test -> Security framework test scenarios (use in VerifyThePageNavigateSteps.java)
 **/
public interface WElement {
    String panelHeading = "body > div > div > div > div > div.panel-heading";
    String tableName = "#maincontentdiv > div.page-body > div > div > div > div.widget-header > span";

    String loginPage_fieldName = "j_username";
    String loginPage_passwordField = "j_password";
    String loginPage_loginButton = "button.btn.btn-md.btn-primary";

    String pendingPasswordRequest = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(6) > a";
    String passwordRequest = "#pendingpasswordrequests > li.dropdown-header.bordered-darkorange";
    String userRequestLine = "#pendingpasswordrequests > li";

    String textLinkSeeAllRequest = "See All Requests";

    String paginationPrevButton = "#example_paginate > ul > li.prev > a";
    String paginationNextButton = "#example_paginate > ul > li.next > a";
    String paginationFirstPage = "#example_paginate > ul > li:nth-child(2) > a";
    String paginationSecondPage = "#example_paginate > ul > li:nth-child(3) > a";
    String paginationThirdPage = "#example_paginate > ul > li:nth-child(4) > a";

    String pagination = "#example_paginate > ul > li";
    String thisIsActivePage = "#example_paginate > ul > li.active > a";
    String cssSelectorForPaginationButton = "#example_paginate > ul > li";
    String bottomTextMessage = "#example_info";

}
