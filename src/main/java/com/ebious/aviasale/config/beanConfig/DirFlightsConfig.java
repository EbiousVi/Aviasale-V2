package com.ebious.aviasale.config.beanConfig;

import com.ebious.aviasale.service.searchFlight.strategies.dirFlStrategy.DefaultDFS;
import com.ebious.aviasale.service.searchFlight.strategies.dirFlStrategy.DirFlightStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DirFlightsConfig {
    private final DefaultDFS defaultDFS;

    @Autowired
    public DirFlightsConfig(DefaultDFS defaultDFS) {
        this.defaultDFS = defaultDFS;
    }

    @Bean
    public List<DirFlightStrategy> dirFlights() {
        List<DirFlightStrategy> dirFlightStrategies = new ArrayList<>();
        dirFlightStrategies.add(defaultDFS);
        return dirFlightStrategies;
    }

}
