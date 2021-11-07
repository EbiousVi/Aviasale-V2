package com.ebious.aviasale.config.beanConfig;

import com.ebious.aviasale.service.searchFlight.searchCommand.FlightType;
import com.ebious.aviasale.service.searchFlight.searchCommand.ConnFlightsCommand;
import com.ebious.aviasale.service.searchFlight.searchCommand.DirFlightsCommand;
import com.ebious.aviasale.service.searchFlight.searchCommand.SearchFlightCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SearchFlightCommandConfig {
    private final DirFlightsCommand dirFlightsCommand;
    private final ConnFlightsCommand connFlightsCommand;

    @Autowired
    public SearchFlightCommandConfig(DirFlightsCommand dirFlightsCommand, ConnFlightsCommand connFlightsCommand) {
        this.dirFlightsCommand = dirFlightsCommand;
        this.connFlightsCommand = connFlightsCommand;
    }

    @Bean
    public Map<FlightType, SearchFlightCommand> commands() {
        Map<FlightType, SearchFlightCommand> map = new HashMap<>();
        map.put(FlightType.DIR, dirFlightsCommand);
        map.put(FlightType.CONN, connFlightsCommand);
        return map;
    }
}
