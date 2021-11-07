package com.ebious.aviasale.service.searchFlight.strategies.dirFlStrategy;

import com.ebious.aviasale.domain.entity.Flights;

import java.util.List;
/**
 * abbreviation Dir = Direct
 */
public interface DirFlightStrategy {
    List<List<Flights>> getDirFlights();
}
