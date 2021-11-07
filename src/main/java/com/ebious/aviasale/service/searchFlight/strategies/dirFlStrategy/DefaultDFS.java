package com.ebious.aviasale.service.searchFlight.strategies.dirFlStrategy;

import com.ebious.aviasale.domain.entity.Flights;
import com.ebious.aviasale.repository.SearchFlightsRepository;
import com.ebious.aviasale.service.SearchQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * abbreviation DFS = Direct Flight Strategy
 */
@Service
public class DefaultDFS implements DirFlightStrategy {

    private final SearchFlightsRepository searchFlightsRepository;
    private final SearchQueryService queryService;

    @Autowired
    public DefaultDFS(SearchFlightsRepository searchFlightsRepository,
                      SearchQueryService queryService) {
        this.searchFlightsRepository = searchFlightsRepository;
        this.queryService = queryService;
    }

    @Override
    public List<List<Flights>> getDirFlights() {
        List<Flights> defaultDirectFlights = searchFlightsRepository.findDefaultDirFlights(
                queryService.getSearchDto().getFrom(),
                queryService.getSearchDto().getTo(),
                queryService.getDayStart(),
                queryService.getDayEnd(),
                queryService.getSearchDto().getNoOfTickets(),
                queryService.getSearchDto().getCondition());
        System.out.println("default direct flight -> size = " + defaultDirectFlights.size());
        if (defaultDirectFlights.isEmpty()) return Collections.emptyList();
        return defaultDirectFlights.stream().map(Collections::singletonList).collect(Collectors.toList());
    }
}
