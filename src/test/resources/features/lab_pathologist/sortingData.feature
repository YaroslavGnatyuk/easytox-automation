Feature: Lab pathologist

  Scenario Outline: Verify data sorting
    Given User login with the physician "cgilabadmin" and password "Welcome@123"

    When Select Settings -> Lab Pathologist.
    Then Lab Pathologist List screen is displayed.

    When Click on down arrow icon on  Sorting  column <column>
    Then Records should be displayed based on the ascending order of the selected  field

    When Click on Up arrow icon  on sorting column <column>
    Then Records should be displayed based on the descending order of the selected field
    Examples:
      | column   |
      | Username |
      | Name           |
      | Lab            |
      | Salutation     |
      | Medicare Num   |
      | Medicaid Num   |