package com.ebious.aviasale.domain.entity;


import com.ebious.aviasale.domain.entity.compositeKeys.TicketNoFlightIdPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "ticket_flights")
@Validated
public class TicketFlights {

    @EmbeddedId
    private TicketNoFlightIdPK ticketFlightsCompositePK;

    @Column(name = "fare_conditions")
    private String fareConditions;

    @Column(name = "amount")
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_no", insertable = false, updatable = false)
    private Tickets ticket;

    @JsonIgnore
    @OneToOne(mappedBy = "ticketFlight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BoardingPasses boardingPass;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", insertable = false, updatable = false)
    private Flights flight;

    public TicketFlights() {
    }

    public TicketFlights(TicketNoFlightIdPK ticketFlightsCompositePK, String fareConditions, BigDecimal amount,
                         Tickets ticket, BoardingPasses boardingPass, Flights flight) {
        this.ticketFlightsCompositePK = ticketFlightsCompositePK;
        this.fareConditions = fareConditions;
        this.amount = amount;
        this.ticket = ticket;
        this.boardingPass = boardingPass;
        this.flight = flight;
    }

    public TicketNoFlightIdPK getTicketFlightsCompositePK() {
        return ticketFlightsCompositePK;
    }

    public void setTicketFlightsCompositePK(TicketNoFlightIdPK ticketFlightsCompositePK) {
        this.ticketFlightsCompositePK = ticketFlightsCompositePK;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Tickets getTicket() {
        return ticket;
    }

    public void setTicket(Tickets ticket) {
        this.ticket = ticket;
    }

    public BoardingPasses getBoardingPass() {
        return boardingPass;
    }

    public void setBoardingPass(BoardingPasses boardingPass) {
        this.boardingPass = boardingPass;
    }

    public Flights getFlight() {
        return flight;
    }

    public void setFlight(Flights flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "TicketFlights{" +
                "ticketFlightsCompositePK=" + ticketFlightsCompositePK +
                ", fareConditions='" + fareConditions + '\'' +
                ", amount=" + amount +
                '}';
    }
}
