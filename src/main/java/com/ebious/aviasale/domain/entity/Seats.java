package com.ebious.aviasale.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "seats")
public class Seats {

    @Embeddable
    public static class SeatsCompositePK implements Serializable {

        @Column(name = "aircraft_code")
        private String aircraftCode;

        @Column(name = "seat_no")
        private String seatNo;

        public SeatsCompositePK() {
        }

        public SeatsCompositePK(String aircraftCode, String seatNo) {
            this.aircraftCode = aircraftCode;
            this.seatNo = seatNo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SeatsCompositePK that = (SeatsCompositePK) o;
            return Objects.equals(aircraftCode, that.aircraftCode) &&
                    Objects.equals(seatNo, that.seatNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(aircraftCode, seatNo);
        }

        public String getAircraftCode() {
            return aircraftCode;
        }

        public void setAircraftCode(String aircraftCode) {
            this.aircraftCode = aircraftCode;
        }

        public String getSeatNo() {
            return seatNo;
        }

        public void setSeatNo(String seatNo) {
            this.seatNo = seatNo;
        }

        @Override
        public String toString() {
            return "SeatsCompositePK{" +
                    "aircraftCode='" + aircraftCode + '\'' +
                    ", seatNo='" + seatNo + '\'' +
                    '}';
        }
    }

    @EmbeddedId
    private SeatsCompositePK seatsCompositePK;

    @Column(name = "fare_conditions")
    private String fareConditions;

    public Seats() {
    }

    public Seats(SeatsCompositePK seatsCompositePK, String fareConditions) {
        this.seatsCompositePK = seatsCompositePK;
        this.fareConditions = fareConditions;
    }

    public SeatsCompositePK getSeatsCompositePK() {
        return seatsCompositePK;
    }

    public void setSeatsCompositePK(SeatsCompositePK seatsCompositePK) {
        this.seatsCompositePK = seatsCompositePK;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    @Override
    public String toString() {
        return "Seats{" +
                "seatsCompositePK=" + seatsCompositePK +
                ", fareConditions='" + fareConditions + '\'' +
                '}';
    }
}
