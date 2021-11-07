package com.ebious.aviasale.domain.enums;

public enum FareConditions {
    ECONOMY("Economy"),
    COMFORT("Comfort"),
    BUSINESS("Business");

    public final String value;

    FareConditions(String value) {
        this.value = value;
    }
}
