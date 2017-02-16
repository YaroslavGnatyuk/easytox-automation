package com.easytox.automation.temp_tests;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleTest {
    List<String> brokenData = new ArrayList<>();
    @Before
    public void init(){
        brokenData.add("POS");
        brokenData.add("1");
        brokenData.add("pos");
    }

    @Test
    public void correctionData() {
        if (brokenData.size()<4){
            if (StringUtils.isNumeric(brokenData.get(0))){

            }
        }
    }
}
