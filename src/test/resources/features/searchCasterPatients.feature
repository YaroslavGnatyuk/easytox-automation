Feature: Security framework test scenarios

  Scenario: Search patients for Caster
    When User login with the physician "Caster" and password "Welcome@123"
    Then User should be able to go to Physician page

    When User clicks on Patient from menu
    Then User should be navigated to Patients list page

    When Check for the patients list
    Then All patients from Zest lab should be displayed

    When Check for the other patients
    Then There should be only patients from ZestLab

    When User search for the patients which are in Zest Lab Client list
    Then Results should be displayed as for the search made

    When User search for the patients from other lab
    Then No results should be displayed when searched with the patients other than Zest Lab Client Patients