package com.ebious.aviasale.domain.apiDto.resp;

import java.util.List;

public class BookingPartDto {
    private FlightDto flightDto;
    private List<PassengerDocDto> passengerDocDtos;

    public BookingPartDto() {
    }

    public FlightDto getFlightDto() {
        return flightDto;
    }

    public void setFlightDto(FlightDto flightDto) {
        this.flightDto = flightDto;
    }

    public List<PassengerDocDto> getPassengerDocDtos() {
        return passengerDocDtos;
    }

    public void setPassengerDocDtos(List<PassengerDocDto> passengerDocDtos) {
        this.passengerDocDtos = passengerDocDtos;
    }

    @Override
    public String toString() {
        return "BookingPartDto{" +
                "flightDto=" + flightDto +
                ", passengerDocDtos=" + passengerDocDtos +
                '}';
    }
}
