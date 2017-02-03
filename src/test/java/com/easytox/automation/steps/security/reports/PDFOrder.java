package com.easytox.automation.steps.security.reports;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PDFOrder extends Order {
    private PDDocument order;
    private static final Logger log = Logger.getLogger(Order.class);

    public PDFOrder() {
    }

    public PDFOrder(PDDocument order){
        this.order = order;
    }

    public PDFOrder getOrderFromPDF() {
        String content = getContentFromReport(order);
        List<String> stringsFromReport = Arrays.asList(content.split("\\r?\\n"));
        log.info(stringsFromReport.size());
        this.parseStringsFromReport(stringsFromReport);

        return this;
    }

    private void parseStringsFromReport(List<String> stringsFromReport) {
        for (String stringFromReport : stringsFromReport) {
            this.setPatientNameAndPhysician(stringFromReport);
            this.setAccession(stringFromReport);
            this.setPatientDOBAndSampleType(stringFromReport);
            this.setCollectedDateAndReceivedInLab(stringFromReport);
            this.setCompound1(stringFromReport);
            this.setCompound2(stringFromReport);
            this.setVCompound1(stringFromReport);
            this.setVCompound2(stringFromReport);
            this.setMedication(stringFromReport);
        }
    }

    private void setMedication(final String stringFromReport) {
        String medication = "Medication(s) :";

        if (stringFromReport.contains(medication)) {
            String tempStringFromReport = stringFromReport.replace(medication, "");

            List<String> data = new ArrayList<>();
            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(",")).toArray(String[]::new));

            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            super.setMedications(data);
        }
    }

    private void setVCompound1(final String stringFromReport) {
        String vCompound1 = "VCompound1 ";

        if (stringFromReport.contains(vCompound1)) {
            String tempStringFromReport = stringFromReport.replace(vCompound1, "");

            List<String> data = new ArrayList<>();
            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(" ")).toArray(String[]::new));

            //I compound two last words here, because I separated those two words in previous line.
            StringBuilder result = new StringBuilder(data.get(data.size() - 2) + " " + data.get(data.size() - 1));
            data.remove(data.size() - 1);
            data.remove(data.size() - 1);
            data.add(result.toString());

            //I'm checking for comparison sign. If we have comparison sign than I compound it with value.
            if (data.size() > 4) {
                StringBuilder twoString = new StringBuilder(data.get(1) + " " + data.get(2));
                data.remove(1);
                data.remove(1);
                data.add(1, twoString.toString());
            }

            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            super.setvCompound1Result(data.get(0));
            super.setvCompound1ReferenceRange(data.get(1));
            super.setvCompound1Concentration(data.get(2));
            super.setvCompound1Comments(data.get(3));
        }
    }

    private void setVCompound2(final String stringFromReport) {
        String vCompound2 = "VCompound2 ";

        if (stringFromReport.contains(vCompound2)) {
            String tempStringFromReport = stringFromReport.replace(vCompound2, "");

            List<String> data = new ArrayList<>();
            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(" ")).toArray(String[]::new));

            //I compound two last words here, because I separated those two words in previous line.
            StringBuilder result = new StringBuilder(data.get(data.size() - 2) + " " + data.get(data.size() - 1));
            data.remove(data.size() - 1);
            data.remove(data.size() - 1);
            data.add(result.toString());

            //I'm checking for comparison sign. If we have comparison sign than I compound it with value.
            if (data.size() > 4) {
                StringBuilder twoString = new StringBuilder(data.get(1) + " " + data.get(2));
                data.remove(1);
                data.remove(1);
                data.add(1, twoString.toString());
            }

            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            super.setvCompound2Result(data.get(0));
            super.setvCompound2ReferenceRange(data.get(1));
            super.setvCompound2Concentration(data.get(2));
            super.setvCompound2Comments(data.get(3));
        }
    }

    private void setCompound1(final String stringFromReport) {
        String compound1 = "Compound1 ";

        if (stringFromReport.contains(compound1)) {
            String tempStringFromReport = stringFromReport.replace(compound1, "");

            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            super.setCompound1Result(data.get(0));
            super.setCompound1Cutoff(data.get(1));
            super.setCompound1Concentration(data.get(2));
            super.setCompound1Comments(data.get(3));
        }
    }

    public void setCompound2(final String stringFromReport) {
        String compound1 = "Compound2 ";

        if (stringFromReport.contains(compound1)) {
            String tempStringFromReport = stringFromReport.replace(compound1, "");

            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            super.setCompound2Result(data.get(0));
            super.setCompound2Cutoff(data.get(1));
            super.setCompound2Concentration(data.get(2));
            super.setCompound2Comments(data.get(3));
        }
    }

    private void setPatientNameAndPhysician(final String stringFromReport) {
        String patient = "Patient Name";
        String physician = "Physician";
        if (stringFromReport.contains(patient) && stringFromReport.contains(physician)) {
            String tempStringFromReport = stringFromReport.replace("Patient Name", "").replace("Physician", "");
            List<String> data = Arrays.asList(tempStringFromReport.split(":"));

            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            super.setPatientName(data.get(1));
            super.setPhysician(data.get(2));
        }
    }

    private void setAccession(final String stringFromReport) {
        String accession = "Accession";

        if (stringFromReport.contains(accession)) {
            List<String> data = Arrays.asList(stringFromReport.split(":"));
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }
            super.setAccessionNumber(data.get(1));
        }

    }

    private void setPatientDOBAndSampleType(final String stringFromReport) {
        String patientDOB = "Patient DOB: ";
        String sampleType = "Sample Type : ";

        if (stringFromReport.contains(patientDOB) && stringFromReport.contains(sampleType)) {
            String tempStringFromReport = stringFromReport.replace(patientDOB, "").replace(sampleType, "");

            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            super.setPatientDOB(data.get(0));
            super.setSampleType(data.get(1));
        }
    }

    private void setCollectedDateAndReceivedInLab(final String stringFromReport) {
        String collectDate = "Collected Date : ";
        String receivedInLab = "Received in Lab: ";

        if (stringFromReport.contains(collectDate) && stringFromReport.contains(receivedInLab)) {
            String tempStringFromReport = stringFromReport.replace(collectDate, "").replace(receivedInLab, "");

            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }

            super.setCollectDate(data.get(0));
            super.setReceivedInLab(data.get(1));
        }
    }

    private String getContentFromReport(PDDocument report) {
        try {
            PDFTextStripper printer = new PDFTextStripper();
            printer.setSortByPosition(true);

            return printer.getText(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkPositionOfLabNameAndLabAddress() {
        String labName = "Sujana LabOne";
        String labAddressLine1 = "1234 Tuttles park drive Dublin";
        String labAddressLine2 = "Ohio USA 43016";
        String labAddressLine3 = "Lab Director:     CLIA ID:";

        try {
            List<TextPositionSequence> posLabName = findSubWords(this.order, 1, labName);

            List<TextPositionSequence> posLabAddress1 = findSubWords(this.order, 1, labAddressLine1);
            List<TextPositionSequence> posLabAddress2 = findSubWords(this.order, 1, labAddressLine2);
            List<TextPositionSequence> posLabAddress3 = findSubWords(this.order, 1, labAddressLine3);

            System.out.println(posLabName.get(0).getX() + "   " + posLabName.get(0).getY());
            System.out.println(posLabAddress1.get(0).getX() + "   " + posLabAddress1.get(0).getY());
            System.out.println(posLabAddress1.get(0).getX() + "   " + posLabAddress2.get(0).getY());
            System.out.println(posLabAddress1.get(0).getX() + "   " + posLabAddress3.get(0).getY());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static List<TextPositionSequence> findSubWords(PDDocument document, int page, String searchTerm) throws IOException {
        final List<TextPositionSequence> hits = new ArrayList<TextPositionSequence>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                TextPositionSequence word = new TextPositionSequence(textPositions);
                String string = word.toString();

                int fromIndex = 0;
                int index;
                while ((index = string.indexOf(searchTerm, fromIndex)) > -1) {
                    hits.add(word.subSequence(index, index + searchTerm.length()));
                    fromIndex = index + 1;
                }
                super.writeString(text, textPositions);
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(document);
        return hits;
    }
}