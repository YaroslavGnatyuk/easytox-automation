package com.easytox.automation.utils;

/**
 * Created by dhanumandla on 24/09/16.
 */
public enum PaymentType {
    PO ("Purchase Order"),
    CREDIT_CARD("Credit Card");

    String name;

    private PaymentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
