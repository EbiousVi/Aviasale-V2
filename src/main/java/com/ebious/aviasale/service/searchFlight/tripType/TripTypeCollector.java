package com.ebious.aviasale.service.searchFlight.tripType;

import com.ebious.aviasale.domain.apiDto.resp.FlightDto;
import com.ebious.aviasale.domain.entity.Flights;
import com.ebious.aviasale.service.searchFlight.searchCommand.FlightType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TripTypeCollector {
    ResponseEntity<?> collectFoundedFlights(FlightType flightType);
    List<List<FlightDto>> collectToFlightDto(List<List<Flights>> flights);
}
