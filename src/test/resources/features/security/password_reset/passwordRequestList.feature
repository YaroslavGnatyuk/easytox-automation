Feature: Security framework test

  Scenario: Password Reset for a lab user

    When Login to Easytox with "SNLabAdmin" and "Test@1234" credentials.
    Then User login should be successful.

    When Click "Pending Password Requests" link.
    Then Following should be displayed:1 Password Requests
    And Forgot Password   "SNLabAdmin" <link>
    And See All Requests <Link>

    When Select "See All Requests".
    Then Password Request List" screen should be displayed.

    When Verify the details displayed in "Password Request List" screen.
    Then Following values should be displayed against each PasswordReset Request: Request
    And Requested by
    And Requested Date
    And Requested Email
    And Resolved by
    And Status
    And Action