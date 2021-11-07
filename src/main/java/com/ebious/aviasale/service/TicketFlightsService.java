package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.entity.BoardingPasses;
import com.ebious.aviasale.domain.entity.TicketFlights;
import com.ebious.aviasale.domain.entity.Tickets;
import com.ebious.aviasale.domain.entity.compositeKeys.TicketNoFlightIdPK;
import com.ebious.aviasale.domain.pojo.Price;
import com.ebious.aviasale.expection.BookingFailedException;
import com.ebious.aviasale.expection.InternalEbiousAppErrorEx;
import com.ebious.aviasale.repository.SeatsRepository;
import com.ebious.aviasale.repository.TicketFlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketFlightsService {
    private final TicketFlightsRepository ticketFlightsRepository;
    private final BoardingPassesService boardingPassesService;
    private final SeatsRepository seatsRepository;

    @Autowired
    public TicketFlightsService(TicketFlightsRepository ticketFlightsRepository,
                                BoardingPassesService boardingPassesService,
                                SeatsRepository seatsRepository) {
        this.ticketFlightsRepository = ticketFlightsRepository;
        this.boardingPassesService = boardingPassesService;
        this.seatsRepository = seatsRepository;
    }

    public void createTicketsFlights(List<Tickets> tickets, Price price, String fareConditions)
            throws BookingFailedException, InternalEbiousAppErrorEx {
        List<TicketFlights> ticketFlights = tickets.stream().map(ticket -> {
            TicketFlights ticketFlight = new TicketFlights();
            TicketNoFlightIdPK ticketFlightsPK = new TicketNoFlightIdPK(ticket.getTicketNo(), price.getFlightId());
            ticketFlight.setTicketFlightsCompositePK(ticketFlightsPK);
            ticketFlight.setFareConditions(fareConditions);
            ticketFlight.setAmount(price.getValue());
            return ticketFlight;
        }).collect(Collectors.toList());
        ticketFlightsRepository.saveAll(ticketFlights);
        ticketFlightsRepository.flush();
    }

    public List<TicketFlights> getAllTicketsFlightsByBookRef(String bookRef) {
        return ticketFlightsRepository.findAllByBookRef(bookRef);
    }

    @Transactional
    public Integer getFreeSeats(Integer flightId, String conditions) {
        Integer freeSeats = ticketFlightsRepository.findFreeSeatsByFlight(flightId, conditions).orElse(0);
        if (freeSeats == 0) return 0;
        if (freeSeats == -1) return seatsRepository.findAllSeatsByConditions(flightId, conditions).orElse(0);
        return freeSeats;
    }


    @Transactional
    public ResponseEntity<?> checkIn(Integer flightId, String ticketNo, String seatNo) {
        TicketNoFlightIdPK ticketNoFlightIdPK = new TicketNoFlightIdPK(ticketNo, flightId);
        Optional<TicketFlights> ticketFlights = ticketFlightsRepository.findById(ticketNoFlightIdPK);
        if (!ticketFlights.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Ticket Flights Not Found! Contact to customer support!"
            );
        }
        BoardingPasses boardingPass = ticketFlights.get().getBoardingPass();
        if (boardingPass == null) {
            boardingPassesService.createBoardingPass(ticketNoFlightIdPK, seatNo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You are already Check in!");
        }
    }
}
