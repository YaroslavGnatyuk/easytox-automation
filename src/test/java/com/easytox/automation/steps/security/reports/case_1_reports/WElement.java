package com.easytox.automation.steps.security.reports.case_1_reports;
/**
 * I hold here web elements for test -> Security framework test scenarios (use in CreateNewOrder.java)
 **/
interface WElement {
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
    String ORDER_LOGGED_IN = "#form > div:nth-child(2) > div:nth-child(1) > div > div > div:nth-child(4) > div:nth-child(2) > div > input";
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
}
