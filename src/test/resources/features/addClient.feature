Feature: client

  Scenario Outline: Add lab client
    When I select LabClient from the settings menu
    Then LabClient should be open

    When Select icon next to search box
    Then Add Lab Client page should be open

    When Enter business name "<labClients>" and enter all the required details and click on Submit
    Then Added Lab Client should be displayed in the list
    Examples:
    |labClients|
      | West  |
      | Quest |
      | Zesty |
