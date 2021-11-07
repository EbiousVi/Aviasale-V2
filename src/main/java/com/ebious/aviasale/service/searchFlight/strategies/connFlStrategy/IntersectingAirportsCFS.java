package com.ebious.aviasale.service.searchFlight.strategies.connFlStrategy;

import com.ebious.aviasale.domain.entity.Flights;
import com.ebious.aviasale.repository.SearchFlightsRepository;
import com.ebious.aviasale.service.SearchQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * abbreviation CFS = Connection Flight Strategy
 */
@Service
public class IntersectingAirportsCFS implements ConnFlightStrategy {

    private final SearchFlightsRepository searchFlightsRepository;
    private final SearchQueryService queryService;

    @Autowired
    public IntersectingAirportsCFS(SearchFlightsRepository searchFlightsRepository,
                                   SearchQueryService queryService) {
        this.searchFlightsRepository = searchFlightsRepository;
        this.queryService = queryService;
    }

    @Override
    public List<List<Flights>> getConnFlights() {
        List<String> intersectingAirports = this.findIntersectingArrivalAirports();
        if (intersectingAirports.isEmpty()) return Collections.emptyList();
        List<Flights> firstFlights = this.findFirstFlight(intersectingAirports);
        if (firstFlights.isEmpty()) return Collections.emptyList();
        List<Flights> secondFlights = this.findSecondFlight(intersectingAirports);
        List<List<Flights>> connFlights = this.collectByDuration(firstFlights, secondFlights);
        System.out.println("intersecting arrival airports connection flight -> size = " + connFlights.size());
        return connFlights;
    }

    /**
     * The duration between flights must not be more than 24 hours and less than 1 hour
     */
    public List<List<Flights>> collectByDuration(List<Flights> firstFlights, List<Flights> secondFlights) {
        if (!firstFlights.isEmpty() && !secondFlights.isEmpty()) {
            List<List<Flights>> connFlights = new ArrayList<>();
            for (Flights first : firstFlights) {
                for (Flights second : secondFlights) {
                    if (first.getAirportTo().equals(second.getAirportFrom())) {
                        Duration duration = Duration.between(first.getArrivalDate(), second.getDepartureDate());
                        long dur = duration.toHours();
                        if (dur >= 1 && dur < 24) {
                            connFlights.add(Arrays.asList(first, second));
                        }
                    }
                }
            }
            if (!connFlights.isEmpty()) return connFlights;
        }
        return Collections.emptyList();
    }

    /**
     * First flight departure FROM departure airport (from search query) TO intersecting airports
     */
    List<Flights> findFirstFlight(List<String> intersectingArvAirports) {
        return searchFlightsRepository.findFlightsByListAirportCode(
                Collections.singletonList(queryService.getSearchDto().getFrom()),
                intersectingArvAirports,
                queryService.getDayStart(),
                queryService.getDayEnd(),
                queryService.getSearchDto().getNoOfTickets(),
                queryService.getSearchDto().getCondition()
        );
    }

    /**
     * Second flight departure FROM intersecting airports TO arrival airport (from search query)
     */
    List<Flights> findSecondFlight(List<String> intersectingArvAirports) {
        return searchFlightsRepository.findFlightsByListAirportCode(
                intersectingArvAirports,
                Collections.singletonList(queryService.getSearchDto().getTo()),
                queryService.getDayStart(),
                queryService.getDayEnd().plusHours(24),
                queryService.getSearchDto().getNoOfTickets(),
                queryService.getSearchDto().getCondition()
        );
    }

    /**
     * Find intersecting airports if direct flight not found
     */
    List<String> findIntersectingArrivalAirports() {
        List<String> intersectAirports = searchFlightsRepository.findIntersectingArrivalAirports(
                queryService.getSearchDto().getFrom(),
                queryService.getSearchDto().getTo()
        );
        if (intersectAirports.isEmpty()) {
            return Collections.emptyList();
        } else {
            return intersectAirports;
        }
    }
}
