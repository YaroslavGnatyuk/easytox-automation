Feature: Security framework test

  Scenario: Password Reset for a lab pathologist
    When Launch application using link: "http://bmtechsol.com:8080/easytox/"
    Then Easytox Login screen should be displayed.

    When Click "Click here" link on Forgot Password.
    Then Forgot Password screen should be displayed.

    When Enter Username as "SPathOne", valid email address and click "Send my Password" button.
    Then "Forgot Password Request Submitted" message should be displayed on the page.

    When Login to Easytox with "SNLabAdmin" and "Test@123" credentials.
    Then User login should be successful.

    When Click "Pending Password Requests" link.
    Then Following should be displayed:1 Password Requests
    And Forgot Password <link>
    And See All Requests <Link>

    When Select "See All Requests".
    Then Password Request List" screen should be displayed.

    When Verify the details displayed for password reset request of "SPathOne".
    Then Following values should be displayed against password reset request : Request -> "ForgotPassword"
    And Requested by -> "SPathOne"
    And Requested Date -> valid date in the format "dd/MMM/yyyy HH:mm:ss"
    And Requested email -> Entered email ID in Step 3 above.
    And Status -> "Pending"
    And Action -> Edit icon.

    When Click on Edit icon against SPathOne request.
    Then Reset Password screen should be displayed.

    When Enter a new password "Welcome@123" in the "New Password" field and click Save.
    Then "SPathOne password reset successfully" message should be displayed.
    And Status of the request should be changed to "Resolved".

    When Login to the Easytox with "SPathOne" and "Welcome@123" credentials.
    Then "Change Password" screen with following fields should be displayed : Username
    And Old Password
    And New Password
    And Confirm Password

    When Verify username "SPathOne" populated in "Username" field.
    Then "SPathOne" should be autopopulated in "username" field and it should NOT be editable.

    When Enter "Welcome@123" in the Old Password field.
    Then User old password should be successful.

    When Enter "Test@1234" in the New Password field.
    Then User new password should be successful.

    When Enter "Test@1234" in the Confirm Password field.
    Then User entry should be successful.

    When Click save.
    Then Login screen with message "Password changed successfully, you can now login" should be displayed.

    When Enter "SPathOne" and "Test@1234" as user credentials and login.
    Then User login should be successful with reset password.