package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.apiDto.resp.FlightDto;
import com.ebious.aviasale.domain.entity.AirportsData;
import com.ebious.aviasale.domain.entity.Flights;
import com.ebious.aviasale.domain.pojo.Price;
import com.ebious.aviasale.repository.SearchFlightsRepository;
import com.ebious.aviasale.utils.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
public class FlightsService {

    private final SearchFlightsRepository searchFlightsRepository;
    private final SearchQueryService queryService;
    private final PriceService priceService;
    private final DistanceUtil distanceUtil;
    @Autowired
    public FlightsService(SearchFlightsRepository searchFlightsRepository, SearchQueryService queryService,
                          PriceService priceService, DistanceUtil distanceUtil) {
        this.searchFlightsRepository = searchFlightsRepository;
        this.queryService = queryService;
        this.priceService = priceService;
        this.distanceUtil = distanceUtil;
    }


    public FlightDto wrapToFlightDto(Flights flight) {
        AirportsData airportDataFrom = flight.getAirportDataFrom();
        AirportsData airportDataTo = flight.getAirportDataTo();
        this.convertFlightDateTime(flight, airportDataFrom, airportDataTo);
        FlightDto flightDto = new FlightDto();
        flightDto.setFlight(flight);
        flightDto.setAircraft(flight.getAircraftData());
        flightDto.setAirportFrom(airportDataFrom);
        flightDto.setAirportTo(airportDataTo);
        Price price = priceService.createPrice(
                flight.getFlightId(),
                flight.getFlightNo(),
                queryService.getSearchDto().getCondition());
        flightDto.setPrice(price);
        Double distance = distanceUtil.distByCoordinates(
                airportDataFrom.getPoint().getCoordinate().getY(),
                airportDataFrom.getPoint().getCoordinate().getX(),
                airportDataTo.getPoint().getY(),
                airportDataTo.getPoint().getX()
        );
        flightDto.setDistance(distance);
        return flightDto;
    }

    public FlightDto wrapToFlightDtoNoPrice(Flights flight) {
        AirportsData airportDataFrom = flight.getAirportDataFrom();
        AirportsData airportDataTo = flight.getAirportDataTo();
        this.convertFlightDateTime(flight, airportDataFrom, airportDataTo);
        FlightDto flightDto = new FlightDto();
        flightDto.setFlight(flight);
        flightDto.setAircraft(flight.getAircraftData());
        flightDto.setAirportFrom(airportDataFrom);
        flightDto.setAirportTo(airportDataTo);
        Double distance = distanceUtil.distByCoordinates(
                airportDataFrom.getPoint().getCoordinate().getY(),
                airportDataFrom.getPoint().getCoordinate().getX(),
                airportDataTo.getPoint().getY(),
                airportDataTo.getPoint().getX()
        );
        flightDto.setDistance(distance);
        flightDto.setPrice(Price.empty());
        return flightDto;
    }

    void convertFlightDateTime(Flights flight, AirportsData airportFrom, AirportsData airportTo) {
        flight.setDepartureDate(
                this.convertDateTimeToAirportTimezone(flight.getDepartureDate(), airportFrom.getTimeZone())
        );
        flight.setArrivalDate(
                this.convertDateTimeToAirportTimezone(flight.getArrivalDate(), airportTo.getTimeZone())
        );
    }

    OffsetDateTime convertDateTimeToAirportTimezone(OffsetDateTime offsetDateTime, String zoneId) {
        return offsetDateTime.withOffsetSameInstant(ZoneId.of(zoneId).getRules().getOffset(Instant.now()));
    }
}
