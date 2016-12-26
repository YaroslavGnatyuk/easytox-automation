package com.easytox.automation.steps.lab_pathologist.ET_006;

/**
 * I hold here web elements for test -> LabPathologist ET_006_Pathologist_Page navigation(use in PageNavigationSteps.java)
 **/
interface WElement {
    String loginPageFieldName = "j_username";
    String loginPagePasswordField = "j_password";
    String loginPageLoginButton = "button.btn.btn-md.btn-primary";

    String pageHeaderTitle = "li.active:nth-child(3)";

    String dropdownToggle = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(8) > a > i";
    String menuIconLabPathologist = "#topmenu > li:nth-child(9) > a";

    String paginationPrevButton = "#example_paginate > ul > li.prev > a";
    String paginationNextButton = "#example_paginate > ul > li.next > a";
    String pagination = "#example_paginate > ul > li";
    String thisIsActivePage = "#example_paginate > ul > li.active > a";
    String bottomTextMessage = "#example_info";
}
