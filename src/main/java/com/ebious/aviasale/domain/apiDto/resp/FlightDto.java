package com.ebious.aviasale.domain.apiDto.resp;

import com.ebious.aviasale.domain.entity.AircraftsData;
import com.ebious.aviasale.domain.entity.AirportsData;
import com.ebious.aviasale.domain.entity.Flights;
import com.ebious.aviasale.domain.pojo.Price;

public class FlightDto {
    private Flights flight;
    private AirportsData airportFrom;
    private AirportsData airportTo;
    private AircraftsData aircraft;
    private Price price;
    private Double distance;

    public FlightDto() {
    }



    public FlightDto(Flights flight, AirportsData airportFrom, AirportsData airportTo,
                     AircraftsData aircraft, Price price, Double distance) {
        this.flight = flight;
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
        this.aircraft = aircraft;
        this.price = price;
        this.distance = distance;
    }

    public Flights getFlight() {
        return flight;
    }

    public void setFlight(Flights flight) {
        this.flight = flight;
    }

    public AirportsData getAirportFrom() {
        return airportFrom;
    }

    public void setAirportFrom(AirportsData airportFrom) {
        this.airportFrom = airportFrom;
    }

    public AirportsData getAirportTo() {
        return airportTo;
    }

    public void setAirportTo(AirportsData airportTo) {
        this.airportTo = airportTo;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public AircraftsData getAircraft() {
        return aircraft;
    }

    public void setAircraft(AircraftsData aircraft) {
        this.aircraft = aircraft;
    }

    @Override
    public String toString() {
        return "FlightDto{" +
                "flight=" + flight +
                ", airportFrom=" + airportFrom +
                ", airportTo=" + airportTo +
                ", aircraft=" + aircraft +
                ", price=" + price +
                ", distance=" + distance +
                '}';
    }
}
