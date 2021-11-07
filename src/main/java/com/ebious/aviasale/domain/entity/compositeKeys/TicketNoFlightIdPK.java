package com.ebious.aviasale.domain.entity.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TicketNoFlightIdPK implements Serializable {
    @Column(name = "ticket_no")
    private String ticketNo;

    @Column(name = "flight_id")
    private Integer flightId;

    public TicketNoFlightIdPK() {
    }

    public TicketNoFlightIdPK(String ticketNo, Integer flightId) {
        this.ticketNo = ticketNo;
        this.flightId = flightId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketNoFlightIdPK that = (TicketNoFlightIdPK) o;

        if (!ticketNo.equals(that.ticketNo)) return false;
        return flightId.equals(that.flightId);
    }

    @Override
    public int hashCode() {
        int result = ticketNo.hashCode();
        result = 31 * result + flightId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TicketNoFlightIdPK{" +
                "ticketNo='" + ticketNo + '\'' +
                ", flightId=" + flightId +
                '}';
    }
}
