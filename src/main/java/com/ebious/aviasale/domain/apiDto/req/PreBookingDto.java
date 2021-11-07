package com.ebious.aviasale.domain.apiDto.req;

import com.ebious.aviasale.domain.pojo.Price;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
public class PreBookingDto {

    @NotEmpty
    private List<PassengerDto> passengers;

    @NotEmpty
    private List<Price> prices;

    public PreBookingDto() {
    }

    public PreBookingDto(List<PassengerDto> passengers, List<Price> prices) {
        this.passengers = passengers;
        this.prices = prices;
    }

    public List<PassengerDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDto> passengers) {
        this.passengers = passengers;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "PreBookingDto{" +
                "passengers=" + passengers +
                ", prices=" + prices +
                '}';
    }
}
