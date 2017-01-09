Feature: Security framework test

  Scenario Outline: Verify data sorting

    When Login to Easytox with "SNLabAdmin" and "Test@1234" credentials.
    Then User login should be successful.

    When Click "Pending Password Requests" link.
    And Select "See All Requests".
    Then Password Request List" screen should be displayed.

    When Click on down arrow icon  on each column name<column>.
    Then Records should be displayed based on the ascending order of the select column.

    When Click on Up arrow icon on each column name<column>.
    Then Records should be displayed based on the descending order of the selected column.
    Examples:
      | column          |
      | Request         |
      | Requested By    |
      | Requested Date  |
      | Requested Email |
      | Resolved By     |
      | Status          |