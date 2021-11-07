package com.ebious.aviasale.domain.apiDto.resp;

import java.math.BigDecimal;
import java.util.List;

/**
 * abbreviation
 * dep = depart flight, rtn = return flight
 * Common dto to depart and return flights
 */
public class DepRtnFlightsPairDto {

    private List<FlightDto> depFlDtos;
    private List<FlightDto> rtnFlDtos;
    private BigDecimal depTotalPrice;
    private BigDecimal rtnTotalPrice;

    public DepRtnFlightsPairDto() {
    }

    public List<FlightDto> getDepFlDtos() {
        return depFlDtos;
    }

    public void setDepFlDtos(List<FlightDto> depFlDtos) {
        this.depFlDtos = depFlDtos;
    }

    public List<FlightDto> getRtnFlDtos() {
        return rtnFlDtos;
    }

    public void setRtnFlDtos(List<FlightDto> rtnFlDtos) {
        this.rtnFlDtos = rtnFlDtos;
    }

    public BigDecimal getDepTotalPrice() {
        return depTotalPrice;
    }

    public void setDepTotalPrice(BigDecimal depTotalPrice) {
        this.depTotalPrice = depTotalPrice;
    }

    public BigDecimal getRtnTotalPrice() {
        return rtnTotalPrice;
    }

    public void setRtnTotalPrice(BigDecimal rtnTotalPrice) {
        this.rtnTotalPrice = rtnTotalPrice;
    }

    @Override
    public String toString() {
        return "DepRtnFlightsPairDto{" +
                "depFlDtos=" + depFlDtos +
                ", rtnFlDtos=" + rtnFlDtos +
                ", depTotalPrice=" + depTotalPrice +
                ", rtnTotalPrice=" + rtnTotalPrice +
                '}';
    }
}
