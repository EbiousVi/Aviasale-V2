package com.ebious.aviasale.service.searchFlight.strategies.connFlStrategy;

import com.ebious.aviasale.domain.entity.Flights;

import java.util.List;

/**
 * abbreviation Conn = Connection
 */
public interface ConnFlightStrategy {
    List<List<Flights>> getConnFlights();
}
