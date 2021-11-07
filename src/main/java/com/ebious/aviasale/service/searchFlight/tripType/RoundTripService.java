package com.ebious.aviasale.service.searchFlight.tripType;

import com.ebious.aviasale.domain.apiDto.resp.FlightsDto;
import com.ebious.aviasale.domain.apiDto.resp.FlightDto;
import com.ebious.aviasale.domain.apiDto.resp.DepRtnFlightsPairDto;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoundTripService implements TripTypeCollector {

    private final EngineImpl engine;
    private final SearchQueryService queryService;
    private final FlightsService flightsService;

    @Autowired
    public RoundTripService(EngineImpl engine, SearchQueryService queryService, FlightsService flightsService) {
        this.engine = engine;
        this.queryService = queryService;
        this.flightsService = flightsService;
    }

    @Override
    public ResponseEntity<?> collectFoundedFlights(FlightType flightType) {
        List<List<FlightDto>> depFlights = this.collectToFlightDto(engine.start(flightType));
        if (depFlights.isEmpty())
            return this.createFlightsNotFoundResp("Depart flights not found by search params! ");
        //Reverse search param to find return flights
        queryService.TENET();
        List<List<FlightDto>> rtnFlights = this.collectToFlightDto(engine.start(flightType));
        if (rtnFlights.isEmpty())
            return this.createFlightsNotFoundResp("Return flights not found by search params! ");
        queryService.TENET();
        List<DepRtnFlightsPairDto> depRtnFlightsPairDtos = this.crossJoinParty(depFlights, rtnFlights);
        if (depRtnFlightsPairDtos.isEmpty())
            return this.createFlightsNotFoundResp("Depart flight arrival date more then Return flight departure date. " +
                    "Nothing found by search params ");
        FlightsDto flightsDto = new FlightsDto();
        flightsDto.setDepRtnFlPairDtos(depRtnFlightsPairDtos);
        flightsDto.setTripType(TripType.ROUND);
        return new ResponseEntity<>(flightsDto, HttpStatus.OK);
    }

    @Override
    public List<List<FlightDto>> collectToFlightDto(List<List<Flights>> flights) {
        if (flights.isEmpty()) return Collections.emptyList();
        return flights.parallelStream()
                .map(fls -> fls.stream().map(flightsService::wrapToFlightDto).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    ResponseEntity<?> createFlightsNotFoundResp(String message) {
        return new ResponseEntity<>(message +
                queryService.getSearchDto().getFrom() + " to " +
                queryService.getSearchDto().getTo(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Create all possible pairs of departure and return flight.
     * Where arrival date of departure flight is before departure date of return flight.
     * Works like SQL cross join
     */
    List<DepRtnFlightsPairDto> crossJoinParty(List<List<FlightDto>> depFlights,
                                              List<List<FlightDto>> rtnFlights) {
        List<DepRtnFlightsPairDto> depRtnFlightsPairDtos = new ArrayList<>();
        for (List<FlightDto> depFl : depFlights) {
            for (List<FlightDto> rtnFl : rtnFlights) {
                if (depFl.get(depFl.size() - 1).getFlight().getArrivalDate()
                        .isBefore(rtnFl.get(0).getFlight().getDepartureDate())) {
                    DepRtnFlightsPairDto depRtnFlightsPairDto = new DepRtnFlightsPairDto();
                    depRtnFlightsPairDto.setDepFlDtos(depFl);
                    depRtnFlightsPairDto.setRtnFlDtos(rtnFl);
                    depRtnFlightsPairDto.setDepTotalPrice(depFl.stream()
                            .map(fl -> fl.getPrice().getValue())
                            .reduce(BigDecimal.valueOf(0), BigDecimal::add));
                    depRtnFlightsPairDto.setRtnTotalPrice(rtnFl.stream()
                            .map(fl -> fl.getPrice().getValue())
                            .reduce(BigDecimal.valueOf(0), BigDecimal::add));
                    depRtnFlightsPairDtos.add(depRtnFlightsPairDto);
                }
            }
        }
        return depRtnFlightsPairDtos;
    }
}
