package com.easytox.automation.steps.security.reports;

import java.util.List;

public class Order {
    String accessionNumber;
    String patientName;
    String patientDOB;
    String collectDate;
    String physician;
    String sampleType;
    String receivedInLab;

    String compound1Result;
    String compound1Cutoff;
    String compound1Concentration;
    String compound1Comments;

    String compound2Result;
    String compound2Cutoff;
    String compound2Concentration;
    String compound2Comments;

    String vCompound1Result;
    String vCompound1ReferenceRange;
    String vCompound1Concentration;
    String vCompound1Comments;

    String vCompound2Result;
    String vCompound2ReferenceRange;
    String vCompound2Concentration;
    String vCompound2Comments;

    List<String> medications;

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientDOB() {
        return patientDOB;
    }

    public void setPatientDOB(String patientDOB) {
        this.patientDOB = patientDOB;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getPhysician() {
        return physician;
    }

    public void setPhysician(String physician) {
        this.physician = physician;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getReceivedInLab() {
        return receivedInLab;
    }

    public void setReceivedInLab(String receivedInLab) {
        this.receivedInLab = receivedInLab;
    }

    public String getCompound1Result() {
        return compound1Result;
    }

    public void setCompound1Result(String compound1Result) {
        this.compound1Result = compound1Result;
    }

    public String getCompound1Concentration() {
        return compound1Concentration;
    }

    public void setCompound1Concentration(String compound1Concentration) {
        this.compound1Concentration = compound1Concentration;
    }

    public String getCompound1Cutoff() {
        return compound1Cutoff;
    }

    public void setCompound1Cutoff(String compound1Cutoff) {
        this.compound1Cutoff = compound1Cutoff;
    }

    public String getCompound1Comments() {
        return compound1Comments;
    }

    public void setCompound1Comments(String compound1Comments) {
        this.compound1Comments = compound1Comments;
    }

    public String getCompound2Result() {
        return compound2Result;
    }

    public void setCompound2Result(String compound2Result) {
        this.compound2Result = compound2Result;
    }

    public String getCompound2Concentration() {
        return compound2Concentration;
    }

    public void setCompound2Concentration(String compound2Concentration) {
        this.compound2Concentration = compound2Concentration;
    }

    public String getCompound2Cutoff() {
        return compound2Cutoff;
    }

    public void setCompound2Cutoff(String compound2Cutoff) {
        this.compound2Cutoff = compound2Cutoff;
    }

    public String getCompound2Comments() {
        return compound2Comments;
    }

    public void setCompound2Comments(String compound2Comments) {
        this.compound2Comments = compound2Comments;
    }

    public String getvCompound1Result() {
        return vCompound1Result;
    }

    public void setvCompound1Result(String vCompound1Result) {
        this.vCompound1Result = vCompound1Result;
    }

    public String getvCompound1ReferenceRange() {
        return vCompound1ReferenceRange;
    }

    public void setvCompound1ReferenceRange(String vCompound1ReferenceRange) {
        this.vCompound1ReferenceRange = vCompound1ReferenceRange;
    }

    public String getvCompound1Concentration() {
        return vCompound1Concentration;
    }

    public void setvCompound1Concentration(String vCompound1Concentration) {
        this.vCompound1Concentration = vCompound1Concentration;
    }

    public String getvCompound1Comments() {
        return vCompound1Comments;
    }

    public void setvCompound1Comments(String vCompound1Comments) {
        this.vCompound1Comments = vCompound1Comments;
    }

    public String getvCompound2Result() {
        return vCompound2Result;
    }

    public void setvCompound2Result(String vCompound2Result) {
        this.vCompound2Result = vCompound2Result;
    }

    public String getvCompound2ReferenceRange() {
        return vCompound2ReferenceRange;
    }

    public void setvCompound2ReferenceRange(String vCompound2ReferenceRange) {
        this.vCompound2ReferenceRange = vCompound2ReferenceRange;
    }

    public String getvCompound2Concentration() {
        return vCompound2Concentration;
    }

    public void setvCompound2Concentration(String vCompound2Concentration) {
        this.vCompound2Concentration = vCompound2Concentration;
    }

    public String getvCompound2Comments() {
        return vCompound2Comments;
    }

    public void setvCompound2Comments(String vCompound2Comments) {
        this.vCompound2Comments = vCompound2Comments;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    @Override
    public String toString() {
        return "Order{" +
                "accessionNumber='" + accessionNumber + '\'' +
                ", patientName='" + patientName + '\'' +
                ", patientDOB='" + patientDOB + '\'' +
                ", collectDate='" + collectDate + '\'' +
                ", physician='" + physician + '\'' +
                ", sampleType='" + sampleType + '\'' +
                ", receivedInLab='" + receivedInLab + '\'' +
                ", compound1Result='" + compound1Result + '\'' +
                ", compound1Concentration='" + compound1Concentration + '\'' +
                ", compound1Cutoff='" + compound1Cutoff + '\'' +
                ", compound1Comments='" + compound1Comments + '\'' +
                ", compound2Result='" + compound2Result + '\'' +
                ", compound2Concentration='" + compound2Concentration + '\'' +
                ", compound2Cutoff='" + compound2Cutoff + '\'' +
                ", compound2Comments='" + compound2Comments + '\'' +
                ", vCompound1Result='" + vCompound1Result + '\'' +
                ", vCompound1ReferenceRange='" + vCompound1ReferenceRange + '\'' +
                ", vCompound1Concentration='" + vCompound1Concentration + '\'' +
                ", vCompound1Comments='" + vCompound1Comments + '\'' +
                ", vCompound2Result='" + vCompound2Result + '\'' +
                ", vCompound2ReferenceRange='" + vCompound2ReferenceRange + '\'' +
                ", vCompound2Concentration='" + vCompound2Concentration + '\'' +
                ", vCompound2Comments='" + vCompound2Comments + '\'' +
                ", medications=" + medications +
                '}';
    }
}