Feature: Security framework test

  Scenario Outline: Verify data sorting

    When Login to Easytox with "SNLabAdmin" and "Test@1234" credentials.
    Then User login should be successful.

    When Click "Pending Password Requests" link.
    And Select "See All Requests".
    Then Password Request List" screen should be displayed.

    When Enter any search criteria <criteria> and click on search icon.
    Then Matching records with entered data <criteria> should be displayed in the Lab Pathologist List.
    Examples:
      | criteria             |
      | SPhyTwo              |
      | 30/Dec/2016 20:47:06 |
      | someEmail@gmail.com  |
      | SNlabadmin           |
      | Resolved             |