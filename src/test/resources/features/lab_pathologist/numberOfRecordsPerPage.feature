Feature: Lab pathologist

  Scenario: Verify Number of records displayed per page.
    Given User login with the physician "cgilabadmin" and password "Welcome@123"

    When Select Settings -> Lab Pathologist.
    Then Lab Pathologist List screen is displayed.

    When Verify the default number of records displayed
    Then Default number 10 should be displayed in the dropdown box

    When Click on dropdown that shows no of records to be displayed on the page
    Then User should be able to view and select the options from the list and the corresponding number of records should be displayed on the page.