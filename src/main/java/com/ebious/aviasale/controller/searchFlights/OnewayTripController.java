package com.ebious.aviasale.controller.searchFlights;

import com.ebious.aviasale.domain.apiDto.req.SearchDto;
import com.ebious.aviasale.service.SearchQueryService;
import com.ebious.aviasale.service.searchFlight.tripType.OnewayTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/one-way")
@Validated
public class OnewayTripController {

    private final SearchQueryService queryService;
    private final OnewayTripService onewayFlight;

    @Autowired
    public OnewayTripController(SearchQueryService queryService, OnewayTripService onewayFlight) {
        this.queryService = queryService;
        this.onewayFlight = onewayFlight;
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchFlights(@RequestBody @NotNull SearchDto searchDto) {
        queryService.setSearchDto(searchDto);
        return onewayFlight.collectFoundedFlights(searchDto.getFlightType());
    }
}