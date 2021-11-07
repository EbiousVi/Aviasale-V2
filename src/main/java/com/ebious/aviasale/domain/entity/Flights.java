package com.ebious.aviasale.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "flights")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Flights {

    @Id
    @Column(name = "flight_id")
    private Integer flightId;

    @Column(name = "flight_no")
    private String flightNo;

    @Column(name = "scheduled_departure")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime departureDate;

    @Column(name = "scheduled_arrival")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime arrivalDate;

    @Column(name = "departure_airport")
    private String airportFrom;

    @Column(name = "arrival_airport")
    private String airportTo;

    @Column(name = "status")
    private String status;

    @Column(name = "aircraft_code")
    private String aircraft;

    @Column(name = "actual_arrival")
    private OffsetDateTime actualArrival;

    @Column(name = "actual_departure")
    private OffsetDateTime actualDeparture;

    @JsonInclude
    @Transient
    private String interval;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_airport", referencedColumnName = "airport_code", insertable = false, updatable = false)
    private AirportsData airportDataFrom;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "arrival_airport", referencedColumnName = "airport_code", insertable = false, updatable = false)
    private AirportsData airportDataTo;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "aircraft_code", insertable = false, updatable = false)
    private AircraftsData aircraftData;

    public Flights() {
    }

    public Flights(Integer flightId, String flightNo,
                   OffsetDateTime departureDate, OffsetDateTime arrivalDate,
                   String airportFrom, String airportTo,
                   String aircraft, String status) {
        this.flightId = flightId;
        this.flightNo = flightNo;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
        this.aircraft = aircraft;
        this.status = status;
    }

    public Flights(Integer flightId, String flightNo, OffsetDateTime departureDate, OffsetDateTime arrivalDate, String airportFrom, String airportTo, String status, String aircraft, AirportsData airportDataFrom, AirportsData airportDataTo, AircraftsData aircraftData) {
        this.flightId = flightId;
        this.flightNo = flightNo;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
        this.status = status;
        this.aircraft = aircraft;
        this.airportDataFrom = airportDataFrom;
        this.airportDataTo = airportDataTo;
        this.aircraftData = aircraftData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flights flights = (Flights) o;
        return flightId.equals(flights.flightId) && flightNo.equals(flights.flightNo) &&
                departureDate.equals(flights.departureDate) && arrivalDate.equals(flights.arrivalDate) &&
                airportFrom.equals(flights.airportFrom) && airportTo.equals(flights.airportTo) &&
                status.equals(flights.status) && aircraft.equals(flights.aircraft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, flightNo, departureDate, arrivalDate, airportFrom, airportTo, status, aircraft);
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public OffsetDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(OffsetDateTime scheduledDeparture) {
        this.departureDate = scheduledDeparture;
    }

    public OffsetDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(OffsetDateTime scheduledArrival) {
        this.arrivalDate = scheduledArrival;
    }

    public String getAirportFrom() {
        return airportFrom;
    }

    public void setAirportFrom(String departureAirport) {
        this.airportFrom = departureAirport;
    }

    public String getAirportTo() {
        return airportTo;
    }

    public void setAirportTo(String arrivalAirport) {
        this.airportTo = arrivalAirport;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public OffsetDateTime getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(OffsetDateTime actualArrival) {
        this.actualArrival = actualArrival;
    }

    public OffsetDateTime getActualDeparture() {
        return actualDeparture;
    }

    public void setActualDeparture(OffsetDateTime actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public String getInterval() {
        return interval;
    }

    @PostLoad
    public void setInterval() {
        Duration duration = Duration.between(this.arrivalDate, this.departureDate);
        this.interval = duration.toString();
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public AirportsData getAirportDataFrom() {
        return airportDataFrom;
    }

    public void setAirportDataFrom(AirportsData airportDataFrom) {
        this.airportDataFrom = airportDataFrom;
    }

    public AirportsData getAirportDataTo() {
        return airportDataTo;
    }

    public void setAirportDataTo(AirportsData airportDataTo) {
        this.airportDataTo = airportDataTo;
    }

    public AircraftsData getAircraftData() {
        return aircraftData;
    }

    public void setAircraftData(AircraftsData aircraftData) {
        this.aircraftData = aircraftData;
    }

    @Override
    public String toString() {
        return "Flights{" +
                "flightId=" + flightId +
                ", flightNo='" + flightNo + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", airportFrom='" + airportFrom + '\'' +
                ", airportTo='" + airportTo + '\'' +
                ", status='" + status + '\'' +
                ", aircraft='" + aircraft + '\'' +
                ", interval='" + interval + '\'' +
                '}';
    }
}