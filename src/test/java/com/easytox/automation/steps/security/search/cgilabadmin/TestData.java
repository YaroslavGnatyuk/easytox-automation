package com.easytox.automation.steps.security.search.cgilabadmin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yroslav on 12/10/16.
 */
public class TestData {
    private static List<Case> cases = initData();

    private static List<Case> initData() {
        cases = new ArrayList<>(3);

        Case first = new Case.Builder()
                .firstName("John")
                .lastName("Doe")
                .dob(LocalDate.of(1999,3,24))
                .status("Completed")
                .physician("ClinicianCGI LastClinicianCGI")
                .caseNumber("UC201608150029")
                .medNumber("1234")
                .builder();

        cases.add(first);

        return cases;
    }

    public static List<Case> getCases() {
        return cases;
    }
}
