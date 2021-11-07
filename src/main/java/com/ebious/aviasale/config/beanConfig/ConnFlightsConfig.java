package com.ebious.aviasale.config.beanConfig;

import com.ebious.aviasale.service.searchFlight.strategies.connFlStrategy.ConnFlightStrategy;
import com.ebious.aviasale.service.searchFlight.strategies.connFlStrategy.IntersectingAirportsCFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ConnFlightsConfig {
    private final IntersectingAirportsCFS intersectingAirportsCFS;

    @Autowired
    public ConnFlightsConfig(IntersectingAirportsCFS intersectingAirportsCFS) {
        this.intersectingAirportsCFS = intersectingAirportsCFS;
    }

    @Bean
    public List<ConnFlightStrategy> connFlights() {
        List<ConnFlightStrategy> connFlightStrategies = new ArrayList<>();
        connFlightStrategies.add(intersectingAirportsCFS);
        return connFlightStrategies;
    }
}
