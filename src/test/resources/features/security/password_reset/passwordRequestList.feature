Feature: Security framework test

  Scenario: Password Reset for a lab user

    When Login to Easytox with "SNLabAdmin" and "Test@123" credentials.
    Then User login should be successful.

    When Click "Pending Password Requests" link.
    Then Following should be displayed:1 Password Requests
    And Forgot Password   "SPhyOne" <link>
    And See All Requests <Link>