package com.ebious.aviasale.service.searchFlight.engine;

import com.ebious.aviasale.domain.entity.Flights;
import com.ebious.aviasale.service.searchFlight.searchCommand.FlightType;

import java.util.List;

public interface Engine {
    List<List<Flights>> start(FlightType flightType);
}
