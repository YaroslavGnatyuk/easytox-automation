Feature: Security framework test

  Scenario: Password Reset screen
    When Launch application using link: "http://bmtechsol.com:8080/easytox/"
    Then Easytox Login screen should be displayed.

    When Verify the text "Forgot Password. Click here."
    Then Text should be displayed with "click here" as link.

    When Click "Click here" link.
    Then Forgot Password screen should be displayed.

    When Verify the details displayed in the Forgot Password screen.
    Then Following should be displayed in the Forgot Password screen:
#        "Enter your Username and email id. Your password will be sent to provided email address" <text>
#        User Name <Input box>
#        Email <Input Box>
#        Send my Password <button>


