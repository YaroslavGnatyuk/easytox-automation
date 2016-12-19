Feature: Security framework test scenarios

  Scenario: Verify the actions performed against each case in the search results.
    Given User login with the physician "cgilabadmin" and password "Welcome@123"

    When Enter any search criteria and click Search
    Then Cases matching entered search criteria are displayed

    When Enter case accession patient, first name, lastname, DOB in the search box
    Then Matching records with entered data should be populated in the case list

    When Click Case Accession number
    Then Case screen should be displayed for editing

    When Verify that for a case with Status as Ready for Pathologist Processing, a delete icon is displayed against the case. Click delete icon.
    Then Case gets deleted after receiving confirmation from the user.

    When Verify that for a case with Status as Finalized, a edit icon is displayed against the case. Click edit icon.
    Then Two radio options are displayed Case Correct and Case Revise

    When Select Case Correct option.
    Then Case screen should be displayed for editing. Correct option.

    When Select Case Revise option.
    Then Case screen should be displayed for editing. Revise option.

    When Verify the Report column for finalized cases.
    Then A PDF icon for viewing the report should be displayed. Upon clicking the icon, case report should be displayed.



