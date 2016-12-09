Feature: Security framework test scenarios

  Scenario: Search patients for Angelia
    When User login with the physician "Angelia" and password "Welcome@123"
    Then User should be able to go to Physician page

    When User clicks on Patient from menu
    Then User should be navigated to Patients list page

    When Check for the patients list
    Then All patients from Quest and West labs should be displayed

    When Check for the other patients
    Then There should be only patients from QuestLab and WestLab

    When User search for all patients from Quest and West labs
    Then Results should be displayed as for the search made

    When User search for the patients from other lab
    Then No results should be displayed when searched with the patients other than Quest and West Lab Client Patients