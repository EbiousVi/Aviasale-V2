package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.apiDto.req.SearchDto;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class SearchQueryService {
    private SearchDto searchDto;
    private LocalDateTime dayStart;

    //The Timestamp is stored in a database with a timezone of +03;
    private final String databaseZoneId = "Europe/Moscow";
    //Time separator
    private final String T = "T";
    //Reserve time before booking closing
    private final Long reserveTime = 9_000L;

    public SearchDto getSearchDto() {
        return searchDto;
    }

    public void setSearchDto(SearchDto searchDto) {
        this.searchDto = searchDto;
    }

    /**
     * Since all available flights in the database are between 2017-08-15 and 2017-09-14
     * Let's imagine that the system is working in real time. The request contains the user's ZoneId.
     * The system compares the time difference between the client and the server.
     * If the offsets are equal, we check if the days of the month are equal,
     * as if the user is looking for a flight for today.
     * If the days are equal, add 2.5 hours to the client's time as a reserve time for buying a ticket.
     * Since the booking closes 2 hours before departure.
     * If the offset of the zones does not match, then we translate the client's time to the server's time.
     * If, after the translated, the client's day of month is equal to the departure day of month,
     * then the client's translated time plus a reserve is added to the departure date.
     * If the translated day of month is one day longer than the departure day of month,
     * then one day and the client's translated time plus a reserve are added to the departure date.
     * If the translated day of month is one day earlier than the departure date,
     * then the departure date is subtracted for one day and the client's translated time plus the reserve is added.
     * In other cases, the start and end times for the day specified in the request are used.
     */
    public LocalDateTime getDayStart() {
        LocalDate departDate = LocalDate.parse(searchDto.getDepartDate());
        ZonedDateTime serverDT = ZonedDateTime.now(ZoneId.of(databaseZoneId));
        ZonedDateTime clientDT = ZonedDateTime.now(ZoneId.of(searchDto.getZoneId()));
        if (serverDT.getOffset().equals(clientDT.getOffset())) {
            if (departDate.getDayOfMonth() == clientDT.getDayOfMonth()) {
                dayStart = LocalDateTime.parse(departDate + T + clientDT.toLocalTime()).plusSeconds(reserveTime);
            } else {
                dayStart = LocalDateTime.parse(departDate + T + LocalTime.MIN);
            }
        } else {
            ZonedDateTime clientToServerDT = clientDT.withZoneSameInstant(ZoneId.of(databaseZoneId));
            if (clientToServerDT.getDayOfMonth() == departDate.getDayOfMonth()) {
                dayStart = LocalDateTime.parse(departDate + T + clientToServerDT.toLocalTime()).plusSeconds(reserveTime);
            } else if (clientToServerDT.getDayOfMonth() - departDate.getDayOfMonth() == 1) {
                dayStart = LocalDateTime.parse(departDate.plusDays(1) + T +
                        clientToServerDT.toLocalTime()).plusSeconds(reserveTime);
            } else if (clientToServerDT.getDayOfMonth() - departDate.getDayOfMonth() == -1) {
                dayStart = LocalDateTime.parse(departDate.minusDays(1) + T +
                        clientToServerDT.toLocalTime()).plusSeconds(reserveTime);
            } else {
                dayStart = LocalDateTime.parse(departDate + T + LocalTime.MIN);
            }
        }
        return dayStart;
    }

    public LocalDateTime getDayEnd() {
        return LocalDateTime.parse(dayStart.toLocalDate() + T + LocalTime.MAX);
    }

    /**
     * The name of method referring to Christopher Nolan's shiz!
     * Search flight queries use SearchDto from SearchQueryService like a parameter.
     * If trip type is Round, we need to swap depart and
     * return date and swap departure airport and arrival airport.
     */
    public void TENET() {
        String from = searchDto.getFrom();
        String to = searchDto.getTo();
        searchDto.setFrom(to);
        searchDto.setTo(from);
        String departDate = searchDto.getDepartDate();
        String returnDate = searchDto.getReturnDate();
        searchDto.setDepartDate(returnDate);
        searchDto.setReturnDate(departDate);
    }
}
