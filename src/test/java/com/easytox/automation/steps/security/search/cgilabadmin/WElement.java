package com.easytox.automation.steps.security.search.cgilabadmin;

/**
 * I hold here web elements for test -> Search Cases -> ET_001_Case Search,
 * ET_002_Result Search and ET_003_Detail Search
 **/

public interface WElement {
    /*for result search cases and case search*/
    String searchButton = "#searchbtn";
    String caseNumberRowInMyTable = "#myTable > tr:nth-child(2) > td:nth-child(1) > a:nth-child(1)";
    String rowInMyTable = "#myTable > tr";
    String filterField = "#caseorder_filter > label > input";
    String statusOfCaseMainTable = "#caseorder > tbody > tr > td:nth-child(7)";
    String caseNumberFieldSearch = "div.row:nth-child(3) > div:nth-child(1) > input:nth-child(1)";
    String medicalRecordNumberFieldSearch = "div.row:nth-child(3) > div:nth-child(2) > input:nth-child(1)";
    String firstNameFieldSearch = "div.row:nth-child(1) > div:nth-child(1) > input:nth-child(1)";
    String lastNameFieldSearch = "div.no-gutter:nth-child(1) > div:nth-child(2) > input:nth-child(1)";
    String dobFieldSearch = "#dateOfBirth > input:nth-child(1)";
    String statusFieldSearch = "#statusFlag";
    String physicianFieldSearch = "#physician";

    String deleteIconInMyTable = "#deleteOrder1 > i";
    String editIconInMyTable = "#caseCorrection > i";
    String confirmationDeletingInMyTable = "#confirmyes";
    String modalWindowCorrectCase1 = "#correctionform1 > div:nth-child(1) > label > span";
    String modalWindowReviseCase1 = "#correctionform1 > div:nth-child(2) > label > span";

    String saveToPDFButton1 = "#myTable > tr:nth-child(2) > td:nth-child(3) > form > a > i";
    String quantityOfCasesPerOnePage = "select.form-control:nth-child(1)";

    /*for detail search cases*/
    String detailSearchButton = "#detsearchbtn";
    String searchButtonOnDetailSearchScreen = "#detailsearchoptbtn";

    String firstNameRow = "#casetable > tbody > tr > td:nth-child(2)";
    String lastNameRow = "#casetable > tbody > tr > td:nth-child(3)";
    String dobRow = "#casetable > tbody > tr > td:nth-child(4)";
    String caseAccessionNumberRow = "#editlink";

    String lastNameFieldDetailSearch = "#lastname";
    String firstNameFieldDetailSearch = "#firstname";
    String dobFieldDetailSearch = "#dob";
    String statusFieldDetailSearch = "#status";

    String deleteButton = "#deleteOrder > i";
    String confirmationDeleting = "#confirmyesdel";
    String resultStringOfDeleting = "#maincontentdiv > div.page-body > div.alert.alert-success.fade.in > strong";

    String pageHeaderTitle = "li.active:nth-child(3)";

    String correctionButton = "#caseCorrection > i";
    String modalWindowCorrectCase = "#correctionform > div:nth-child(1) > label > span";
    String modalWindowReviseCase = "#correctionform > div:nth-child(2) > label > span";
    String closeEditModalWindowButton = "#caseCorrectionModal > div > div > div.modal-header > button";

    String createNewCaseButton = "#caseorder_wrapper > div.toolbar > a > i";

    String saveToPDFButton = "#caseorder > tbody > tr:nth-child(2) > td:nth-child(9) > form > a > i";
}
