package com.ebious.aviasale.service.searchFlight.searchCommand;

import com.ebious.aviasale.service.searchFlight.strategies.dirFlStrategy.DirFlightStrategy;
import com.ebious.aviasale.domain.entity.Flights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Order(1)
public class DirFlightsCommand implements SearchFlightCommand {
    private final List<DirFlightStrategy> dirFlights;

    @Autowired
    public DirFlightsCommand(List<DirFlightStrategy> dirFlights) {
        this.dirFlights = dirFlights;
    }

    @Override
    public List<List<Flights>> getPossibleFlights() {
        List<List<Flights>> allDirFlights = new ArrayList<>();
        for (DirFlightStrategy dirFlightStrategy : dirFlights) {
            allDirFlights.addAll(dirFlightStrategy.getDirFlights());
        }
        return allDirFlights;
    }
}
