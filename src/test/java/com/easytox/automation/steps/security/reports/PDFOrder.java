package com.easytox.automation.steps.security.reports;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PDFOrder extends Order {
    private PDDocument order;
    private String signedDate;
    private boolean reportIsSigned;
    private static final Logger log = Logger.getLogger(PDFOrder.class);

    public PDFOrder() {
    }

    public PDFOrder(PDDocument order) {
        this.order = order;
    }

    public PDFOrder fillAllFields() {
        String content = getContentFromReport();

        this.parseStringsFromReport(content);

        return this;
    }

    private void parseStringsFromReport(String content) {
        List<String> stringsFromReport = Arrays.asList(content.split("\\r?\\n"));
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
            this.setValidationCompound1And2(content);
            this.setSignedDate(stringFromReport);
            this.checkReportSignature();
        }
    }

    private void setSignedDate(final String stringFromReport) {
        String signedDate = "Signed Date: ";
        if (stringFromReport.contains(signedDate)) {
            // TODO: 3/10/17 remove this comment
            /*List<String> data = Arrays.stream(stringFromReport.split(":"))
                    .map(String::trim)
                    .collect(Collectors.toList());*/
            List<String> data = new ArrayList<>(Arrays.asList(stringFromReport.split(":")));
            for (int j = 0; j < data.size(); j++) {
                data.set(j,data.get(j).trim());
            }
            int indexOfDate = 1;
            this.signedDate = data.get(indexOfDate);
        }
    }

    private void checkReportSignature() {
        try {
            PDFTextStripper parser = new PDFTextStripper();
            parser.setSortByPosition(true);
            parser.getText(order);
            PDPage page = parser.getCurrentPage();
            PDResources resources = page.getResources();

            Iterator<COSName> images = resources.getXObjectNames().iterator();
            if (images.hasNext()) {
                reportIsSigned = true;
            } else {
                reportIsSigned = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setValidationCompound1And2(String content) {
        String signedDate = "Signed Date: ";
        String medication = "Medication(s) :";

        int indexOfSignedDate = 0;
        int indexOfMedication = 0;

        String compound1 = "Compound1 ";
        String compound2 = "Compound2 ";

        List<String> stringsFromReport = Arrays.asList(content.split("\\r?\\n"));
        for (int i = 0; i < stringsFromReport.size(); i++) {
            if (stringsFromReport.get(i).contains(medication)) {
                indexOfMedication = i;
            }

            if (stringsFromReport.get(i).contains(signedDate)) {
                indexOfSignedDate = i;
            }
        }

        if (content.contains(signedDate) && content.contains(medication)) {
            for (int i = 0; i < stringsFromReport.size(); i++) {
                if (i < indexOfSignedDate && i > indexOfMedication) {
                    if (stringsFromReport.get(i).contains(compound1)) {
                        String tempStringFromReport = stringsFromReport.get(i).replace(compound1, "");
                        // TODO: 3/10/17 remove this comment
                        /*List<String> data = Arrays.stream(tempStringFromReport.split(" "))
                                .map(String::trim)
                                .collect(Collectors.toList());*/
                        List<String> data = new ArrayList<>(Arrays.asList(tempStringFromReport.split(" ")));
                        for (int j = 0; j < data.size(); j++) {
                            data.set(j,data.get(j).trim());
                        }
                        if (data.size() < 4) {
                            super.setValidationCompound1Result("default");
                            super.setValidationCompound1Cutoff("default");
                            super.setValidationCompound1Concentration("default");
                            super.setValidationCompound1Comments("default");
                        } else if (data.size() == 4) {
                            super.setValidationCompound1Result(data.get(0));
                            super.setValidationCompound1Cutoff(data.get(1));
                            super.setValidationCompound1Concentration(data.get(2));
                            super.setValidationCompound1Comments(data.get(3));
                        }
                    }
                    if (stringsFromReport.get(i).contains(compound2)) {
                        String tempStringFromReport = stringsFromReport.get(i).replace(compound2, "");
                        // TODO: 3/10/17 remove this comment
                        /*List<String> data = Arrays.stream(tempStringFromReport.split(" "))
                                .map(String::trim)
                                .collect(Collectors.toList());*/

                        List<String> data = new ArrayList<>(Arrays.asList(tempStringFromReport.split(" ")));
                        for (int j = 0; j < data.size(); j++) {
                            data.set(j,data.get(j).trim());
                        }
                        if (data.size() < 4) {
                            super.setValidationCompound2Result("default");
                            super.setValidationCompound2Cutoff("default");
                            super.setValidationCompound2Concentration("default");
                            super.setValidationCompound2Comments("default");
                        } else if (data.size() == 4) {
                            super.setValidationCompound2Result(data.get(0));
                            super.setValidationCompound2Cutoff(data.get(1));
                            super.setValidationCompound2Concentration(data.get(2));
                            super.setValidationCompound2Comments(data.get(3));
                        }
                    }
                }
            }
        }
    }

    private void setMedication(final String stringFromReport) {
        String medication = "Medication(s) :";

        if (stringFromReport.contains(medication)) {
            String stringWithMedicationFromReport = stringFromReport.replace(medication, "");
            if(stringWithMedicationFromReport.isEmpty()){
                return;
            }

            String []arrayFromReport = stringWithMedicationFromReport.split(",");
            List<String> data = new ArrayList<>(Arrays.asList(arrayFromReport));
            // TODO: 3/10/17 remove this comment
//            Collections.addAll(data, Arrays.stream(stringWithMedicationFromReport.split(",")).toArray(String[]::new));

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

            String []arrayFromReport = tempStringFromReport.split(" ");
            List<String> data = new ArrayList<>(Arrays.asList(arrayFromReport));

            // TODO: 3/10/17 remove this comment
//            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(" ")).toArray(String[]::new));

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

            String []arrayFromReport = tempStringFromReport.split(" ");
            List<String> data = new ArrayList<>(Arrays.asList(arrayFromReport));
            // TODO: 3/10/17 remove this comment
//            Collections.addAll(data, Arrays.stream(tempStringFromReport.split(" ")).toArray(String[]::new));

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
            if (data.size() < 4) {
                super.setCompound1Result("default");
                super.setCompound1Cutoff("default");
                super.setCompound1Concentration("default");
                super.setCompound1Comments("default");
            } else if (data.size() == 4) {
                super.setCompound1Result(data.get(0));
                super.setCompound1Cutoff(data.get(1));
                super.setCompound1Concentration(data.get(2));
                super.setCompound1Comments(data.get(3));
            }
        }
    }

    public void setCompound2(final String stringFromReport) {
        String compound2 = "Compound2 ";
        if (stringFromReport.contains(compound2)) {
            String tempStringFromReport = stringFromReport.replace(compound2, "");
            List<String> data = Arrays.asList(tempStringFromReport.split(" "));
            if (data.size() < 4) {
                super.setCompound2Result("default");
                super.setCompound2Cutoff("default");
                super.setCompound2Concentration("default");
                super.setCompound2Comments("default");
            } else if (data.size() == 4) {
                super.setCompound2Result(data.get(0));
                super.setCompound2Cutoff(data.get(1));
                super.setCompound2Concentration(data.get(2));
                super.setCompound2Comments(data.get(3));
            }
            for (int i = 0; i < data.size(); i++) {
                data.set(i, data.get(i).trim());
            }
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

            super.setPatientName(correctPatientName(data.get(1)));
            super.setPhysician(data.get(2));
        }
    }

    private String correctPatientName(String patientName) {
        int firstName = 0, lastName = 1;
        String[] name = patientName.split("  ");
        return name[firstName] + " " + name[lastName];
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

    public String getContentFromReport() {
        try {
            PDFTextStripper printer = new PDFTextStripper();
            printer.setSortByPosition(true);

            return printer.getText(this.order);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isPositionOfLabNameAndLabAddressValid() {
        int validXCoordinate = 350;
        int validYCoordinate = 80;

        String labName = "Sujana LabOne";
        String labAddressLine1 = "1234 Tuttles park drive Dublin";
        String labAddressLine2 = "Ohio USA 43016";
        String labAddressLine3 = "Lab Director:     CLIA ID:";

        try {
            List<TextPositionSequence> posLabName = findSubWords(this.order, 1, labName);

            List<TextPositionSequence> posLabAddress1 = findSubWords(this.order, 1, labAddressLine1);
            List<TextPositionSequence> posLabAddress2 = findSubWords(this.order, 1, labAddressLine2);
            List<TextPositionSequence> posLabAddress3 = findSubWords(this.order, 1, labAddressLine3);

            if ((posLabName.get(0).getX() > 350 && posLabName.get(0).getY() < 80) &&
                    (posLabAddress1.get(0).getX() > validXCoordinate && posLabAddress1.get(0).getY() < validYCoordinate) &&
                    (posLabAddress2.get(0).getX() > validXCoordinate && posLabAddress2.get(0).getY() < validYCoordinate) &&
                    (posLabAddress3.get(0).getX() > validXCoordinate && posLabAddress3.get(0).getY() < validYCoordinate)) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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

    public PDDocument getOrder() {
        return order;
    }

    public void setOrder(PDDocument order) {
        this.order = order;
    }

    public String getSignedDate() {
        return signedDate;
    }

    public boolean isReportIsSigned() {
        return reportIsSigned;
    }
}