package com.easytox.automation.temp_tests;
/**
 * I hold here web elements for test -> Security framework test scenarios (use in CreateNewOrder1.java)
 **/
interface WElement {

    String AMOUNT_OF_RECORDS_PER_ONE_PAGE = "#caseorder_length > label > select";
    String LOGIN_PAGE_FIELD_NAME = "j_username";
    String LOGIN_PAGE_PASSWORD_FIELD = "j_password";
    String LOGIN_PAGE_LOGIN_BUTTON = "button.btn.btn-md.btn-primary";
    String CREATE_NEW_ORDER = "#sidebar > ul > li:nth-child(2) > a";
    String ORDER_LOCATION = "#physicianLocation";
    String ORDER_PATIENT = "#patient";
    String ORDER_DATE = "#form > div:nth-child(2) > div:nth-child(1) > div > div > div:nth-child(3) > div:nth-child(2) > div > label";
    String ORDER_DATE_PICKER = "/html/body/div[4]";
    String ACTIVE_DATE = "td.active.day";
    String MONTH_AND_YEAR = "/html/body/div[4]/div[1]/table/thead/tr[1]/th[2]";
    String ORDER_CASE = "#caseType";
    String ORDER_LOGGED_IN = "#form > div:nth-child(2) > div:nth-child(1) > div > div > div:nth-child(7) > div > div > input";

    String ORDER_PRESCRIBE_MEDICINE = "#e2";
    String ORDER_PRIMARY_PHYSICIAN = "#form > div:nth-child(2) > div:nth-child(2) > div > div > div:nth-child(1) > div > div > b";
    String PATHOLOGIST_GROUP = "#form > div:nth-child(2) > div:nth-child(2) > div > div > div:nth-child(3) > div:nth-child(1) > div";
    String ORDER_SELECT_PATHOLOGIST = "#e3";
    String ORDER_COMPOUND_PROFILE = "#profile";
    String ORDER_COMPOUND1 = "#compounds\\5b 0\\5d \\2e compound";
    String ORDER_COMPOUND2 = "#compounds\\5b 1\\5d \\2e compound";
    String ORDER_VCOMPOUND1 = "#validitycompounds\\5b 0\\5d \\2e compound";
    String ORDER_VCOMPOUND2 = "#validitycompounds\\5b 1\\5d \\2e compound";
    String ORDER_SUBMIT = "#form > div:nth-child(3) > div > div > button";
    String ONE_ROW_IN_ORDER_LIST = "#caseorder > tbody > tr";
    String ACTIVE_PAGE = "#maincontentdiv > div.page-breadcrumbs > ul > li.active";
    String PAGE_TITLE_AFTER_CREATING_ORDER = "#maincontentdiv > div.page-body > div.alert.alert-success.fade.in";
    String LOGIN_AREA = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(1) > a";

    String PENDING_ORDERS = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li:nth-child(4) > a > i";
    String NEW_ORDER_CELL = "#caseorder > tbody > tr:nth-child(1) > td.sorting_1 > form";
    String ACCEPT_NEW_ORDER = "#acceptform";

    String SEARCH_ORDER_FIELD = "#caseorder_filter > label > input";
    String ONE_COLUMN_IN_ROW = "#caseorder > tbody > tr > td";

    String RESULT_TEST_COMMENTS_FOR_COMPOUND_1 = "#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(7) > div > div:nth-child(5) > div > div.col-sm-9 > div > input";
    String RESULT_TEST_COMMENTS_FOR_COMPOUND_2 ="#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(12) > div > div:nth-child(5) > div > div.col-sm-9 > div > input";
    String RESULT_TEST_COMMENTS_FOR_VCOMPOUND_1 ="#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(4) > div > div:nth-child(5) > div > div.col-sm-9 > div > input";
    String RESULT_TEST_COMMENTS_FOR_VCOMPOUND_2 ="#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(8) > div > div:nth-child(5) > div > div.col-sm-9 > div > input";
    String UPDATE_ORDER = "submitbtn";
    String CASE_ACCESSION_ON_UPDATE_CASE_PAGE = "#form > div > div > div.well.bg-blue > div > div:nth-child(2) > div:nth-child(1)";
    String xxxxxxx_POPUP_CLOSE = "#com_asprise_scan_prompt > a";
    String TASKS = "body > div.navbar > div > div > div.navbar-header.pull-right > div > ul > li.open > a";

    String COMPOUND_1_CONCENTRATION = "#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(7) > div > div:nth-child(4) > div > input";
    String COMPOUND_2_CONCENTRATION ="#compounds > div > div > div.panel.panel-success > div.panel-body > div:nth-child(12) > div > div:nth-child(4) > div > input";
    String VCOMPOUND_1_CONCENTRATION ="#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(4) > div > div:nth-child(4) > div > input";
    String VCOMPOUND_2_CONCENTRATION ="#compounds > div > div > div.panel.panel-info > div.panel-body > div:nth-child(8) > div > div:nth-child(4) > div > input";

    String REPORT_DOWNLOAD_ICON = "#caseorder > tbody > tr > td:nth-child(9) > form > a > i";

}
