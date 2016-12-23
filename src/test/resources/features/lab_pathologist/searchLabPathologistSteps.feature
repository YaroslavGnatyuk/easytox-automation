Feature: Lab pathologist

  Scenario Outline: Verify the Search Results.
    Given User login with the physician "cgilabadmin" and password "Welcome@123"

    When Select Settings -> Lab Pathologist.
    Then Lab Pathologist List screen is displayed.

    When Enter any search criteria <criteria> and click on search icon.
    Then Matching records with entered data <criteria> should be displayed in the Lab Pathologist List.
    Examples:
      | criteria        |
      | CGIPathologist2 |
      | Pathologist two |
      | CGI DEMO Lab    |
      | Mr              |
      | 321             |
      | 123             |