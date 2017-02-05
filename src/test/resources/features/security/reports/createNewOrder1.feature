Feature: Security framework test

  Scenario: Create new order 1

    When Login to Easytox with "PhyOnee" and "Test@123" credentials.
    Then User login should be successful.

    When Select 'Create Order' from left hand menu.
    Then 'Create Order' page should be displayed.

    When Verify the value displayed in 'Location' drop down.
    Then "LabClientOne" should be displayed in the Location drop down.

    When Select "LabClientOne" from the location
    Then User should be able to select the desired value "LabClientOne" successfully.

    When Select Patient "Patient Test" from the 'Patient' drop down.
    Then Patient "Patient Test" should be selected successfully.

    When Enter a valid Date Collected.
    Then Default Date should be today's date. User should be able to select any other date.

    When Select a Case Type
    Then Case Type should be selected successfully.

    When Verify value in Loggedin By field.
    Then Value in Loggedin By should be populated with "PhyOnee".

    When Select Prescribed Medicine as "Drug1"
    Then User selection should be successful.

    When Select primary Physician as "Phy Onee".
    Then User selection of Physician should be "Phy Onee" successful.

    When Select Pathologist as "Path One"
    Then User selection of Pathologist "Path One" should be successful.

    When Select Compound Profile as "TestProfile1"
    Then User selection of compound profile "TestProfile1" should be successful.

    When Verify the details displayed when Compound Profile is selected.
    Then Following details should be populated in Create Order screen:Test Screen - with values "Compound1" and "Compound2"
    And Validity Testing - with values "VCompound1" and "VCompound2"

    When Click Submit
    Then Order List screen with newly created order should be displayed.

    When Login to EasyTox with credentials "PathOne" and "Test@123"
    Then User login should be successful again.

    When Click Pending Orders.
    Then Orders List screen should be displayed.

    When Verify that the order created in above case is displayed in the Orders List.
    Then Newly created order should be displayed in the list.

    When Click on Order Number.
    Then "Update CaseOrder" screen should be displayed.

    When Click Accept Order.
    Then Order should be converted to Case and "Update Case" screen should be displayed.

    When Under Test Screen section, select concentration for 'Compound1' such that test results are "Positive Result".
    Then Selections should be made as appropriately for 'Compound1'.

    When Under Test Screen section, select concentration for 'Compound2' such that test results are "Positive Result".
    Then Selections should be made as appropriately for 'Compound2'.

    When Under Validity Testing section, select concentration for 'VCompound1' in such a way that test results are "Normal Result".
    Then Selections should be made as appropriately for 'VCompound1'.

    When Under Validity Testing section, select concentration for 'VCompound2' in such a way that test results are "Normal Result".
    Then Selections should be made as appropriately for 'VCompound2'.

    When Click Update.
    Then Case List screen should be populated with Case 'Status' as "Ready for pathologist".

    When Select 'Tasks' from the top menu.
    Then Above Case should be listed under 'Cases in Pending' section.

    When Select Case # from the 'Cases in Pending' list.
    Then "Update Case" screen is displayed.

    When Select 'Finalized' radio option.
    Then A confirmation message "Are you sure you want to finalize the case. Please verify the results before finalizing" should be displayed.

    When Click Finalize and enter Sign Pin when it prompts for Sign Pin.
    Then Case should be finalized successfully.


