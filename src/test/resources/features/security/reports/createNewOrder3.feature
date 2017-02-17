Feature: Security framework test

  Scenario: Create new order 3

    When Login to Easytox with "PhyTwo" and "Test@123" credentials.
    Then User login should be successful.

    When Select 'Create Order' from left hand menu.
    Then 'Create Order' page should be displayed.

    When Verify the value displayed in 'Location' drop down.
    Then "LabClientTwo" should be displayed in the Location drop down.

    When Select "LabClientTwo" from the location
    Then User should be able to select the desired value "LabClientTwo" successfully.

    When Select Patient "Test PatientTwo" from the 'Patient' drop down.
    Then Patient "Test PatientTwo" should be selected successfully.

    When Enter a valid Date Collected.
    Then Default Date should be today's date. User should be able to select any other date.

    When Select a Case Type
    Then Case Type should be selected successfully.

    When Verify value in Loggedin By field.
    Then Value in Loggedin By should be populated with "PhyTwo".

    When Select Prescribed Medicine as "Drug1" and "Drug2"
    Then User selection should be successful.

    When Select primary Physician as "Phy Two".
    Then User selection of Physician should be "Phy Two" successful.

    When Select Pathologist as "Path Two"
    Then User selection of Pathologist "Path Two" should be successful.

    When Select Compound Profile as "TestProfile1"
    Then User selection of compound profile "TestProfile1" should be successful.

    When Verify the details displayed when Compound Profile is selected.
    Then Following details should be populated in Create Order screen:Test Screen - with values "Compound1" and "Compound2"
    And Validity Testing - with values "VCompound1" and "VCompound2"

    When Click Submit
    Then Order List screen with newly created order should be displayed.
#Case Finalize
    When Login to EasyTox with credentials "PathTwo" and "Test@123"
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

    When Under Test Screen section, select concentration for 'Compound2' such that test results are "Negative Result".
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
#    Verify report
    When Click on PDF icon under 'Report' column of finalized case.
    Then Report should be opened in the PDF format.

    When Verify the details displayed in the report.
    Then Lab Name along with lab address should be displayed on the top right of the screen.

    When Verify the details of order displayed in the report.
    Then Following details should be displayed in the report: Accession Number: Value should match the data entered on Case entry
    And Patient Name: Value should match the data entered on Case entry
    And Patient DOB: Value should match the data entered on Case entry
    And Collected Date: Value should match the data entered on Case entry
    And Physician: Value should match the data entered on Case entry
    And Sample Type: Value should match the data entered on Case entry
    And Received in Lab: Value should match the data entered on Case entry

    When Verify the details displayed in "Consistent Results-Reported Medication Detected" section.
    Then Following details should be displayed Compound1:Result - "POS"
    And Conc. Compound1 - <Value entered on Case Entry>
    And Cutoff Compound1 - <Value entered on Case Entry>
    #   In bottom line in order to test case should be "Pos" instead of "Positive"
    And Comments Compound1 - "Positive"

    When Verify the details displayed in "Inconsistent Results - Unexpected Negatives for Medications" section Compound2.
    Then Following details Compound2: Result - "NEG"
    And Conc. Compound2 - <Value entered on Case Entry>
    And Cutoff Compound2 - <Value entered on Case Entry>
    And Comments Compound2 - "Neg"

    When Verify the details displayed in 'Inconsistent Results - Unexpected PositiveS' "Inconsistent Results - Unexpected Positives" section.
    Then No values should be displayed in 'Inconsistent Results - Unexpected Positives' section.

    #    In bottom line in order to test case should be "Specimen Validity Testing" instead of "SPECIMEN VALIDITY TESTING"
    When Verify the details displayed in 'Specimen Validity Testing' "SPECIMEN VALIDITY TESTING" section.
    Then Data entered in this section should be same as the data entered in 'Specimen Validity Testing' section during Case Entry.

    When Verify "Medications"
    Then "Drug1","Drug2" should be displayed under 'Medications'.

    When Verify details from "Test Screen Validation" section Test Screen Validation are displayed .
    Then Details from 'Test Screen Validation' section in the case entry should be displayed.

    When Verify Signature
    Then Signature of Pathologist along with Signed Date should be displayed.