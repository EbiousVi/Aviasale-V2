package com.ebious.aviasale.domain.apiDto.req;

import com.ebious.aviasale.service.searchFlight.searchCommand.FlightType;
import com.ebious.aviasale.service.searchFlight.tripType.TripType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class SearchDto {
    @NotNull
    @Size(min = 3, max = 3)
    private String from;
    @NotNull
    @Size(min = 3, max = 3)
    private String to;
    @NotNull
    @Size(min = 8, max = 9)
    private String departDate;
    @NotNull
    @Size(min = 8, max = 9)
    private String returnDate;
    @NotBlank
    private String zoneId;
    @NotBlank
    private String condition;
    @NotNull
    private Integer noOfTickets;
    @NotNull
    private TripType tripType;
    @NotNull
    private FlightType flightType;

    public SearchDto() {
    }

    public SearchDto(String from, String to, String departDate, String returnDate,
                     String zoneId, String condition, Integer noOfTickets, TripType tripType, FlightType flightType) {
        this.from = from;
        this.to = to;
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.zoneId = zoneId;
        this.condition = condition;
        this.noOfTickets = noOfTickets;
        this.tripType = tripType;
        this.flightType = flightType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(Integer noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departDate='" + departDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", zoneId='" + zoneId + '\'' +
                ", condition='" + condition + '\'' +
                ", noOfTickets=" + noOfTickets +
                ", tripType=" + tripType +
                ", flightType=" + flightType +
                '}';
    }
}

