package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.enums.FareConditions;
import com.ebious.aviasale.domain.pojo.Price;
import com.ebious.aviasale.repository.TicketFlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PriceService {
    private final TicketFlightsRepository ticketFlightsRepository;

    @Autowired
    public PriceService(TicketFlightsRepository ticketFlightsRepository) {
        this.ticketFlightsRepository = ticketFlightsRepository;
    }

    public Price createPrice(Integer flightId, String flightNo, String fareConditions) {
        Price price = new Price();
        price.setFlightId(flightId);
        price.setValue(this.calculatePrice(flightNo, fareConditions));
        price.setFareConditions(fareConditions);
        return price;
    }

    public BigDecimal calculatePrice(String flightNo, String fareConditions) {
        BigDecimal price = this.calculatePriceFromDB(flightNo, fareConditions);
        if (price.equals(BigDecimal.ZERO)) {
            return this.calculateRandomPrice(fareConditions);
        } else {
            return price;
        }
    }

    /**
     * Calculates the cost as the arithmetic average of all purchased tickets by Flight Number.
     * for more info {@link TicketFlightsRepository#avgFlightCostByFlightNoAndFareConditions(String, String)}
     * But on some flights there were no flights at all, then return Zero.
     * In this case, generate a random price for each fare condition.
     */
    public BigDecimal calculatePriceFromDB(String flightNo, String fareConditions) {
        Optional<BigDecimal> priceValue =
                ticketFlightsRepository.avgFlightCostByFlightNoAndFareConditions(flightNo, fareConditions);
        return priceValue.orElse(BigDecimal.ZERO);
    }

    /**
     * Generate a random price for each fare condition.
     */
    public BigDecimal calculateRandomPrice(String fareConditions) {
        if (fareConditions.equals(FareConditions.ECONOMY.value)) {
            return BigDecimal.valueOf(5000D + Math.round(Math.random() * 5000));
        } else if (fareConditions.equalsIgnoreCase(FareConditions.COMFORT.value)) {
            return BigDecimal.valueOf(12000D + Math.round(Math.random() * 10000));
        } else if (fareConditions.equalsIgnoreCase(FareConditions.BUSINESS.value)) {
            return BigDecimal.valueOf(23000D + Math.round(Math.random() * 15000));
        }
        return BigDecimal.valueOf(Integer.MAX_VALUE);
    }
}
