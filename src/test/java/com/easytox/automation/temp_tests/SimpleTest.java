package com.easytox.automation.temp_tests;

import org.junit.Test;

public class SimpleTest {

    @Test
    public String correctPatientName(String patientName) {
        patientName = "Phy  Onee";
        String[] name = patientName.split("  ");
        return patientName = name[0] + " " + name[1];
    }
}
