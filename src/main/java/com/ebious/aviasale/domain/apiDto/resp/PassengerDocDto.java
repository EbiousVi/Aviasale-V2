package com.ebious.aviasale.domain.apiDto.resp;

import java.math.BigDecimal;

public class PassengerDocDto {
    private Integer flightId;
    private String ticketNo;
    private String passengerId;
    private String fullName;
    private String fareConditions;
    private String seatNo;
    private BigDecimal amount;

    public PassengerDocDto() {
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PassengerDocDto{" +
                "flightId=" + flightId +
                ", ticketNo='" + ticketNo + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", fareConditions='" + fareConditions + '\'' +
                ", seatNo='" + seatNo + '\'' +
                ", amount=" + amount +
                '}';
    }
}
