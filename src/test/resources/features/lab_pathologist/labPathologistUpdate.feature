Feature: Lab pathologist

  Scenario: Verify updating a lab pathologist.
    Given User login with the physician "cgilabadmin" and password "Welcome@123"

    When Select Settings -> Lab Pathologist.
    Then Lab Pathologist List screen is displayed.

    When Click Edit icon for an existing lab pathologist.
    Then Update Lab Pathologist' screen is displayed.

    When Verify User Information section.
    Then User Information should be locked for editing.

    When Make changes to Personal/Clinician Information and click update.
    Then Lab pathologist should be updated successfully.