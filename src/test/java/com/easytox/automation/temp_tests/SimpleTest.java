package com.easytox.automation.temp_tests;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleTest {
    private String example = " Sujana LabOne\n" +
            "1234 Tuttles park drive Dublin\n" +
            "Ohio USA 43016\n" +
            "Lab Director:     CLIA ID:\n" +
            "Accession #: AA17-135\n" +
            "Patient Name : Patient  Test Physician : Phy Onee\n" +
            "Patient DOB: 02/01/1970 Sample Type : Urine\n" +
            "Collected Date : 02/04/2017 Received in Lab: 02/04/2017\n" +
            "Summary Report\n" +
            "Consistent Results-Reported Medication Detected\n" +
            "Prescription/ Analyte Result Conc. Detection Cutoff(ng/mL) Window (ng/mL) Comments\n" +
            "Class1\n" +
            "Compound1 POS 1 1 Positive\n" +
            "Inconsistent Results - Unexpected Positives\n" +
            "Prescription/ Analyte Result Conc.(ng/mL) Detection Window\n" +
            "Cutoff\n" +
            "(ng/mL) Comments\n" +
            "Class2\n" +
            "Compound2 POS 5 5 Pos\n" +
            "SPECIMEN VALIDITY TESTING\n" +
            "Test Test Outcome Reference Range Conc. (ng/mL) Comments\n" +
            "Class1\n" +
            "VCompound1 NORMAL = 10 10 Normal Result\n" +
            "Class2\n" +
            "VCompound2 NORMAL < 5 3 Normal Result\n" +
            "Medication(s) : Drug1\n" +
            "Compound Pos/Neg Cutoff (ng/mL) Conc. (ng/mL) Comments\n" +
            "Class1\n" +
            "Compound1 POS 1 1 Positive\n" +
            "Class2\n" +
            "Compound2 POS 5 5 Pos\n" +
            "Path One\n" +
            "Signed Date: 02/04/2017\n" +
            "Page 1";

    @Test
    public void correctPatientName() {

        List<String> stringsFromReport = Arrays.asList(example.split("\\r?\\n"));
        String signedDate = "Signed Date: ";
        for (int i = 0; i < stringsFromReport.size(); i++) {
            String stringFromReport = stringsFromReport.get(i);
            if (stringFromReport.contains(signedDate)) {
                List<String> data = Arrays.stream(stringFromReport.split(":"))
                        .map(String::trim)
                        .collect(Collectors.toList());
                int indexOfDate = 1;
                System.out.println(data.get(indexOfDate));
            }else{
                System.out.println("null");
            }
        }
    }
}
