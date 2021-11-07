package com.ebious.aviasale.controller.boarding;

import com.ebious.aviasale.domain.apiDto.resp.SeatDto;
import com.ebious.aviasale.service.SeatsService;
import com.ebious.aviasale.service.TicketFlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
public class BoardingController {

    private final TicketFlightsService ticketFlightsService;
    private final SeatsService layoutService;

    @Autowired
    public BoardingController(
            TicketFlightsService ticketFlightsService,
            SeatsService layoutService) {
        this.ticketFlightsService = ticketFlightsService;
        this.layoutService = layoutService;
    }

    @GetMapping("/layoutService")
    public List<List<SeatDto>> getCabinLayout(@RequestParam("flightId") @NotNull Integer flightId,
                                        @RequestParam("aircraft") @NotBlank String aircraft,
                                        @RequestParam("conditions") @NotBlank String fareConditions) {
        return layoutService.buildCabinLayout(flightId, aircraft, fareConditions);
    }

    @GetMapping("/boarding")
    public ResponseEntity<?> doBoarding(@RequestParam("flightId") @NotNull Integer flightId,
                                      @RequestParam("ticketNo") @NotBlank String ticketNo,
                                      @RequestParam("seatNo") @NotBlank String seatNo) {
        return ticketFlightsService.checkIn(flightId, ticketNo, seatNo);
    }
}
