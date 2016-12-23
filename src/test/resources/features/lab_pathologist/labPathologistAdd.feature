Feature: Lab pathologist

  Scenario: Verify adding a new lab pathologist.
    Given User login with the physician "cgilabadmin" and password "Welcome@123"

    When Select Settings -> Lab Pathologist.
    Then Lab Pathologist List screen is displayed.

    When Click Add New Pathologist icon.
    Then Add Lab Pathologist' screen is displayed.

    When Enter all the information in the screen and click Submit.
    Then New Lab Pathologist is created successfully.