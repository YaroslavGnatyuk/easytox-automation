package com.easytox.automation.temp_tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleTest {

    @Test
    public void setPatientNameAndPhysician() {
        String stringFromReport = "Medication(s) : Drug1";
        String medication = "Medication(s) :";

        if (stringFromReport.contains(medication)) {
            String tempStringFromReport = stringFromReport.replace(medication, "");

            List<String> data = new ArrayList<>();
            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(",")).toArray(String[]::new));


            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            data.forEach(System.out::println);
        }
    }
}
