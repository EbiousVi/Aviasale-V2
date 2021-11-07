package com.ebious.aviasale.service.searchFlight.searchCommand;

import com.ebious.aviasale.domain.entity.Flights;

import java.util.List;

/**
 * List<Flights> - An atomic entity representing the unit of the result,
 * since flight from point A to point B directly are not always possible.
 * And at the same time, to simplify the display on the front-end.
 */
public interface SearchFlightCommand {
    List<List<Flights>> getPossibleFlights();
}
