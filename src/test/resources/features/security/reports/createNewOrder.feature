Feature: Security framework test

  Scenario: Create new order

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

