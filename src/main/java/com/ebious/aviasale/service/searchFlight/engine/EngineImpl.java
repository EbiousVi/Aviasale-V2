package com.ebious.aviasale.service.searchFlight.engine;

import com.ebious.aviasale.domain.entity.Flights;
import com.ebious.aviasale.service.searchFlight.searchCommand.FlightType;
import com.ebious.aviasale.service.searchFlight.searchCommand.SearchFlightCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EngineImpl implements Engine {
    private final Map<FlightType, SearchFlightCommand> commands;

    @Autowired
    public EngineImpl(Map<FlightType, SearchFlightCommand> commands) {
        this.commands = commands;
    }

    @Override
    public List<List<Flights>> start(FlightType flightType) {
        List<List<Flights>> allFlights = new ArrayList<>();
        if (flightType.equals(FlightType.ALL)) {
            for (FlightType type : FlightType.values()) {
                if (type.equals(FlightType.ALL)) continue;
                allFlights.addAll(commands.get(type).getPossibleFlights());
            }
        } else {
            allFlights.addAll(commands.get(flightType).getPossibleFlights());
        }
        return allFlights;
    }

}