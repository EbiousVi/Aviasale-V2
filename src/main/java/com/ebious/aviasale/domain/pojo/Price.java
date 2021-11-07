package com.ebious.aviasale.domain.pojo;

import com.ebious.aviasale.domain.enums.FareConditions;

import java.math.BigDecimal;

public class Price {
    private Integer flightId;
    private BigDecimal value;
    private String fareConditions;

    public Price() {
    }

    public Price(Integer flightId, BigDecimal value, String fareConditions) {
        this.flightId = flightId;
        this.value = value;
        this.fareConditions = fareConditions;
    }

    public static Price empty() {
        return new Price(0, BigDecimal.ZERO, FareConditions.ECONOMY.value);
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    @Override
    public String toString() {
        return "Price{" +
                "flightId=" + flightId +
                ", value=" + value +
                ", fareConditions=" + fareConditions +
                '}';
    }
}
