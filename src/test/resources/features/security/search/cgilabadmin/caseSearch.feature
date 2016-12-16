Feature: Security framework test scenarios

  Scenario: Verify that user can search for any case by entering search criteria.
    Given User login with the physician "cgilabadmin" and password "Welcome@123"

    When Enter a valid first name in the First Name field and click search
    Then All the cases with matching entered first name should be displayed in the Search results

    When Enter a valid last name in the Last Name field and click search
    Then All the cases with matching entered last  name should be displayed in the Search results

    When Enter a valid DOB in the Date of Birth field in MM/DD/YYYY format and click search
    Then All the cases with matching DOB should be displayed in the Search results

    Then Select a value from the Status drop down and click search
    Then All the cases in selected status should be displayed in the Search results

    When Select a value from the Physician drop down and click search
    Then All the cases related to selected physician should be displayed in the Search results

    When Enter a valid case number and click search
    Then Matching case should be displayed in the  Search results

    When Enter a valid Medical Record Number and click search
    Then Matching case should be displayed in the Search results

    When Enter values in multiple fields and click Search
    Then Cases matching all the entered conditions should be displayed in the Search results

    When Click Search without entering values in any field
    Then All the cases should be returned