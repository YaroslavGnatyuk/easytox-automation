package com.easytox.automation.temp_tests;


import com.easytox.automation.steps.security.reports.PDFOrder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class SimpleTest {
    String path = "/home/yroslav/case#4/AA17-139.PDF";
    PDFOrder pdf;

    @Test
    public void correctionData() {
        pdf = new PDFOrder();
        try {
            PDDocument doc = PDDocument.load(new File(path));
            pdf.setOrder(doc);
            pdf.fillAllFields();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(pdf.isReportIsSigned());
        assertTrue(pdf.isPositionOfLabNameAndLabAddressValid());

        System.out.println(pdf.getSignedDate());
    }
}
