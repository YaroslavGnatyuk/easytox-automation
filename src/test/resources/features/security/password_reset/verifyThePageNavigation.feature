Feature: Security framework test

  Scenario Outline: Verify the page navigation

    When Login to Easytox with "SNLabAdmin" and "Test@1234" credentials.
    Then User login should be successful.

    When Click "Pending Password Requests" link.
    And Select "See All Requests".
    Then Password Request List" screen should be displayed.

    When Navigate back and forth by selecting page numbers <button>
    Then User should be navigate to the selected page<button>

    When Checking the message of no of records displayed on the current page bottom left corner of the screen
    Then A text message “Showing x to y of z entries” should be displayed on the bottom left corner of the list.

    Examples: | button |
    | Next   |
    | 1      |
    | 2      |
    | 3      |
    | Prev   |
