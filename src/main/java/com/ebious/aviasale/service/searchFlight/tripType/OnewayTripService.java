package com.ebious.aviasale.service.searchFlight.tripType;

import com.ebious.aviasale.domain.apiDto.resp.FlightsDto;
import com.ebious.aviasale.domain.apiDto.resp.DepRtnFlightsPairDto;
import com.ebious.aviasale.domain.apiDto.resp.FlightDto;
import com.ebious.aviasale.domain.entity.Flights;
import com.ebious.aviasale.service.FlightsService;
import com.ebious.aviasale.service.SearchQueryService;
import com.ebious.aviasale.service.searchFlight.engine.EngineImpl;
import com.ebious.aviasale.service.searchFlight.searchCommand.FlightType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OnewayTripService implements TripTypeCollector {

    private final EngineImpl engine;
    private final FlightsService flightsService;
    private final SearchQueryService queryService;

    @Autowired
    public OnewayTripService(EngineImpl engine, FlightsService flightsService, SearchQueryService queryService) {
        this.engine = engine;
        this.flightsService = flightsService;
        this.queryService = queryService;
    }

    @Override
    public ResponseEntity<?> collectFoundedFlights(FlightType flightType) {
        List<List<FlightDto>> flightDtos = this.collectToFlightDto(engine.start(flightType));
        if (flightDtos.isEmpty())
            return new ResponseEntity<>("Nothing found by search params! "
                    + queryService.getSearchDto().getFrom() + " to "
                    + queryService.getSearchDto().getTo(),
                    HttpStatus.NOT_FOUND);

        List<DepRtnFlightsPairDto> depRtnFlightsPairDtos = flightDtos.stream()
                .map(flDto -> {
                    DepRtnFlightsPairDto depRtnFlightsPairDto = new DepRtnFlightsPairDto();
                    depRtnFlightsPairDto.setDepFlDtos(flDto);
                    depRtnFlightsPairDto.setRtnFlDtos(Collections.emptyList());
                    depRtnFlightsPairDto.setDepTotalPrice(flDto.stream()
                            .map(dto -> dto.getPrice().getValue())
                            .reduce(BigDecimal.valueOf(0), BigDecimal::add));
                    return depRtnFlightsPairDto;
                })
                .collect(Collectors.toList());

        FlightsDto flightsDto = new FlightsDto(depRtnFlightsPairDtos, TripType.ONEWAY);
        return new ResponseEntity<>(flightsDto, HttpStatus.OK);
    }

    @Override
    public List<List<FlightDto>> collectToFlightDto(List<List<Flights>> flights) {
        if (flights.isEmpty()) return Collections.emptyList();
        return flights.parallelStream()
                .map(fls -> fls.stream().map(flightsService::wrapToFlightDto).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
