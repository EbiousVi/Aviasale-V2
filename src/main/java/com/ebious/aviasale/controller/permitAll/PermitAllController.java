package com.ebious.aviasale.controller.permitAll;

import com.ebious.aviasale.domain.apiDto.resp.AirportDto;
import com.ebious.aviasale.service.AirportsDataService;
import com.ebious.aviasale.service.TicketFlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
public class PermitAllController {
    private final AirportsDataService airportsDataService;
    private final TicketFlightsService ticketFlightsService;

    @Autowired
    public PermitAllController(AirportsDataService airportsDataService,
                               TicketFlightsService ticketFlightsService) {
        this.airportsDataService = airportsDataService;
        this.ticketFlightsService = ticketFlightsService;

    }

    @GetMapping("/airports")
    public List<AirportDto> getAllAirportDto() {
        return airportsDataService.getAllAirportsDto();
    }

    @GetMapping("/free-seats")
    public Integer getFreeSeats(@RequestParam("flightId") @NotNull Integer flightId,
                                @RequestParam("conditions") @NotBlank String fareConditions) {
        return ticketFlightsService.getFreeSeats(flightId, fareConditions);
    }
}
