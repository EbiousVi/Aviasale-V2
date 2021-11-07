package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.apiDto.req.PreBookingDto;
import com.ebious.aviasale.domain.apiDto.resp.BookingDto;
import com.ebious.aviasale.domain.apiDto.resp.BookingPartDto;
import com.ebious.aviasale.domain.apiDto.resp.PassengerDocDto;
import com.ebious.aviasale.domain.entity.*;
import com.ebious.aviasale.domain.pojo.Price;
import com.ebious.aviasale.expection.BookingFailedException;
import com.ebious.aviasale.expection.InternalEbiousAppErrorEx;
import com.ebious.aviasale.expection.InvalidUserDataException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TryBookingService {
    private final BookingsService bookingsService;
    private final TicketFlightsService ticketFlightsService;
    private final TicketsService ticketsService;
    private final FlightsService flightsService;
    private static final Logger logger = LoggerFactory.getLogger(TicketFlightsService.class);

    @Autowired
    public TryBookingService(BookingsService bookingsService, TicketFlightsService ticketFlightsService,
                             TicketsService ticketsService, FlightsService flightsService) {
        this.bookingsService = bookingsService;
        this.ticketFlightsService = ticketFlightsService;
        this.ticketsService = ticketsService;
        this.flightsService = flightsService;
    }

    /**
     * Method catch database trigger Error, if available seats are over
     * or incompatible conditions are passed when try to book.
     * <a href="file:src/main/resources/db/migration/V4__add_booking_system.sql"/> Trigger code
     * Trigger pg_advisory_lock(). How booking system works go to Readme.md
     */
    public void tryBooking(PreBookingDto preBookingDto, Users user)
            throws BookingFailedException, InternalEbiousAppErrorEx, InvalidUserDataException {
        try {
            this.booking(preBookingDto, user);
        } catch (JpaSystemException e) {
            String TRIGGER_ERROR_CODE = "P0001";
            String incompatibleConditionsErrMsg =
                    "The Flight is incompatible with the fare conditions or Flight with flight id does not exist";
            String noFreeSeatsErrMsg = "No free seats";
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            if (rootCause instanceof PSQLException) {
                String sqlState = ((PSQLException) rootCause).getSQLState();
                if (sqlState.equals(TRIGGER_ERROR_CODE)) {
                    String detail = ((PSQLException) rootCause).getServerErrorMessage().getDetail();
                    String message = ((PSQLException) rootCause).getServerErrorMessage().getMessage();
                    if (message.equals(noFreeSeatsErrMsg)) {
                        logger.info(rootCause.getMessage() + detail);
                        throw new BookingFailedException("Free seats are over!", HttpStatus.NOT_FOUND);
                    }
                    if (message.equals(incompatibleConditionsErrMsg)) {
                        String msg = "Booking Failed by Server Error!";
                        logger.error(msg, rootCause);
                        throw new InternalEbiousAppErrorEx(msg, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            } else {
                logger.error("CreateTicketsFlights unexpected exception", rootCause);
                throw new InternalEbiousAppErrorEx("Contact to customer support!",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Transactional
    public void booking(PreBookingDto preBookingDto, Users user) throws
            BookingFailedException, InvalidUserDataException, InternalEbiousAppErrorEx {
        Bookings booking = bookingsService.createBooking(
                user,
                preBookingDto.getPrices(),
                preBookingDto.getPassengers().size());
        List<Tickets> tickets = ticketsService.createTickets(booking, preBookingDto.getPassengers());
        for (Price price : preBookingDto.getPrices()) {
            ticketFlightsService.createTicketsFlights(tickets, price, price.getFareConditions());
        }
    }

    public List<BookingDto> getBookingDto(Users user) throws InternalEbiousAppErrorEx {
        List<Bookings> bookings = bookingsService.getBookingsByUser(user);
        if (bookings.isEmpty()) return Collections.emptyList();
        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Bookings booking : bookings) {
            BookingDto bookingDto = new BookingDto();
            bookingDto.setBooking(booking);
            List<TicketFlights> ticketFlights = ticketFlightsService.getAllTicketsFlightsByBookRef(booking.getBookRef());
            Map<Integer, List<TicketFlights>> ticketFlightsByFlightId = ticketFlights.stream()
                    .collect(Collectors.groupingBy(tf -> tf.getTicketFlightsCompositePK().getFlightId()));
            List<BookingPartDto> bookingPartDtos = this.collectBookingPartDtos(ticketFlightsByFlightId);
            bookingDto.setBookingPartDtos(bookingPartDtos);
            bookingDtos.add(bookingDto);
        }
        return bookingDtos;
    }

    List<BookingPartDto> collectBookingPartDtos(Map<Integer, List<TicketFlights>> ticketFlightsMap)
            throws InternalEbiousAppErrorEx {
        List<BookingPartDto> bookingPartDtos = new ArrayList<>();
        for (Map.Entry<Integer, List<TicketFlights>> tfMap : ticketFlightsMap.entrySet()) {
            BookingPartDto bookingPartDto = new BookingPartDto();
            bookingPartDto.setFlightDto(tfMap.getValue().stream()
                    .map(TicketFlights::getFlight)
                    .map(flightsService::wrapToFlightDtoNoPrice)
                    .findFirst()
                    .orElseThrow(() -> new InternalEbiousAppErrorEx(
                            "Unexpected error, findFirst not found",
                            HttpStatus.INTERNAL_SERVER_ERROR)));
            List<PassengerDocDto> passengerDocDtos = this.collectPassengerDocDtos(tfMap.getValue());
            bookingPartDto.setPassengerDocDtos(passengerDocDtos);
            bookingPartDtos.add(bookingPartDto);
        }
        bookingPartDtos.sort((Comparator.comparing(o -> o.getFlightDto().getFlight().getDepartureDate())));
        return bookingPartDtos;
    }

    List<PassengerDocDto> collectPassengerDocDtos(List<TicketFlights> ticketFlights) {
        List<PassengerDocDto> passengerDocDtos = new ArrayList<>();
        for (TicketFlights ticketFlight : ticketFlights) {
            PassengerDocDto passengerDocDto = new PassengerDocDto();
            passengerDocDto.setFlightId(ticketFlight.getTicketFlightsCompositePK().getFlightId());
            passengerDocDto.setTicketNo(ticketFlight.getTicketFlightsCompositePK().getTicketNo());
            BoardingPasses boardingPass = ticketFlight.getBoardingPass();
            if (boardingPass == null) {
                passengerDocDto.setSeatNo(null);
            } else {
                passengerDocDto.setSeatNo(boardingPass.getSeatNo());
            }
            passengerDocDto.setFareConditions(ticketFlight.getFareConditions());
            passengerDocDto.setAmount(ticketFlight.getAmount());
            passengerDocDto.setFullName(ticketFlight.getTicket().getFullName());
            passengerDocDto.setPassengerId(ticketFlight.getTicket().getPassengerId());
            passengerDocDtos.add(passengerDocDto);
        }
        return passengerDocDtos;
    }
}

