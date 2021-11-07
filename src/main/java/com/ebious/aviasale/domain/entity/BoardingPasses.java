package com.ebious.aviasale.domain.entity;


import com.ebious.aviasale.domain.entity.compositeKeys.TicketNoFlightIdPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "boarding_passes")
@Validated
public class BoardingPasses {

    @EmbeddedId
    private TicketNoFlightIdPK boardingPassesCompositePK;

    @Column(name = "boarding_no")
    private Integer boardingNo;

    @Column(name = "seat_no")
    private String seatNo;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumns({
            @PrimaryKeyJoinColumn(name = "ticket_no"),
            @PrimaryKeyJoinColumn(name = "flight_id")
    })
    private TicketFlights ticketFlight;

    public BoardingPasses() {
    }

    public BoardingPasses(TicketNoFlightIdPK boardingPassesCompositePK, Integer boardingNo, String seatNo) {
        this.boardingPassesCompositePK = boardingPassesCompositePK;
        this.boardingNo = boardingNo;
        this.seatNo = seatNo;
    }

    public TicketNoFlightIdPK getBoardingPassesCompositePK() {
        return boardingPassesCompositePK;
    }

    public void setBoardingPassesCompositePK(TicketNoFlightIdPK boardingPassesCompositePK) {
        this.boardingPassesCompositePK = boardingPassesCompositePK;
    }

    public Integer getBoardingNo() {
        return boardingNo;
    }

    public void setBoardingNo(Integer boardingNo) {
        this.boardingNo = boardingNo;
    }

    @NotNull
    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public TicketFlights getTicketFlight() {
        return ticketFlight;
    }

    public void setTicketFlight(TicketFlights ticketFlight) {
        this.ticketFlight = ticketFlight;
    }

    @Override
    public String toString() {
        return "BoardingPasses{" +
                "boardingPassesCompositePK=" + boardingPassesCompositePK +
                ", boardingNo=" + boardingNo +
                ", seatNo='" + seatNo + '\'' +
                '}';
    }
}
