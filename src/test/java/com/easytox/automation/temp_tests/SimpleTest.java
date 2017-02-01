package com.easytox.automation.temp_tests;

import java.util.Arrays;
import java.util.List;

public class SimpleTest {
    public void setPatientNameAndPhysician() {
        String demo = "Patient Name : Patient  Test Physician : Phy Onee";
        if(demo.contains("Patient Name") && demo.contains("Physician")){
            System.out.println("Catch!!!");

            demo = demo.replace("Patient Name", "");
            demo = demo.replace("Physician", "");

            List<String> data = Arrays.asList(demo.split(":"));

            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            data.forEach(System.out::println);
        }
    }


}
