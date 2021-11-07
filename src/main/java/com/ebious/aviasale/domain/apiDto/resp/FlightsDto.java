package com.ebious.aviasale.domain.apiDto.resp;

import com.ebious.aviasale.service.searchFlight.tripType.TripType;

import java.util.List;

public class FlightsDto {
    private List<DepRtnFlightsPairDto> depRtnFlPairDtos;
    private TripType tripType;

    public FlightsDto() {
    }

    public FlightsDto(List<DepRtnFlightsPairDto> depRtnFlPairDtos, TripType tripType) {
        this.depRtnFlPairDtos = depRtnFlPairDtos;
        this.tripType = tripType;
    }

    public List<DepRtnFlightsPairDto> getDepRtnFlPairDtos() {
        return depRtnFlPairDtos;
    }

    public void setDepRtnFlPairDtos(List<DepRtnFlightsPairDto> depRtnFlPairDtos) {
        this.depRtnFlPairDtos = depRtnFlPairDtos;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    @Override
    public String toString() {
        return "FlightsDto{" +
                "depRtnFlPairDtos=" + depRtnFlPairDtos +
                ", tripType=" + tripType +
                '}';
    }
}
