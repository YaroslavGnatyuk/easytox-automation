package com.easytox.automation.steps.security.search.cgilabadmin;

import java.time.LocalDate;

/**
 * Created by yroslav on 12/11/16.
 */
public class Case {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String status;
    private String physician;
    private String caseNumber;
    private String medNumber;

    public static class Builder {
        private String firstName = "";
        private String lastName = "";
        private LocalDate dob = LocalDate.now();
        private String status = "";
        private String physician = "";
        private String caseNumber = "";
        private String medNumber = "";

        public Builder firstName(String firstName){
            if(firstName == null){
                throw new NullPointerException("First name is null");
            }
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName){
            if(lastName == null){
                throw new NullPointerException("Last name is null");
            }
            this.lastName = lastName;
            return this;
        }

        public Builder dob(LocalDate dob){
            if(dob == null){
                throw new NullPointerException("Data of birthday is null");
            }
            this.dob = dob;
            return this;
        }

        public Builder status(String status){
            if(status == null){
                throw new NullPointerException("Status is null");
            }
            this.status = status;
            return this;
        }

        public Builder physician(String physician){
            if(physician == null){
                throw new NullPointerException("Physician is null");
            }
            this.physician = physician;
            return this;
        }

        public Builder caseNumber(String caseNumber){
            if(caseNumber == null){
                throw new NullPointerException("Case number is null");
            }
            this.caseNumber = caseNumber;
            return this;
        }

        public Builder medNumber(String medNumber){
            if(medNumber == null){
                throw new NullPointerException("Med number is null");
            }

            this.medNumber = medNumber;
            return this;
        }

        public Case builder(){
            return new Case(this);
        }
    }

    private Case(Builder builder){
        firstName = builder.firstName;
        lastName = builder.lastName;
        dob = builder.dob;
        status = builder.status;
        physician = builder.physician;
        caseNumber = builder.caseNumber;
        medNumber = builder.medNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getStatus() {
        return status;
    }

    public String getPhysician() {
        return physician;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public String getMedNumber() {
        return medNumber;
    }
}

