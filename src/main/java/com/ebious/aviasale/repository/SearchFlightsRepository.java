package com.ebious.aviasale.repository;

import com.ebious.aviasale.domain.entity.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchFlightsRepository extends JpaRepository<Flights, Integer> {

    @Query(value = "SELECT * FROM flights AS fl " +
            "WHERE departure_airport IN :airportsFrom " +
            "AND arrival_airport IN :airportsTo " +
            "AND scheduled_departure BETWEEN :dayStart AND :dayEnd " +
            "AND status IN ('Scheduled', 'On Time', 'Delayed') " +
            "AND check_free_seats(fl.flight_id, :noOfTickets, fl.aircraft_code, :fareConditions) " +
            "ORDER BY scheduled_departure", nativeQuery = true)
    List<Flights> findFlightsByListAirportCode(@Param("airportsFrom") List<String> airportsFrom,
                                               @Param("airportsTo") List<String> airportsTo,
                                               @Param("dayStart") LocalDateTime dayStart,
                                               @Param("dayEnd") LocalDateTime dayEnd,
                                               @Param("noOfTickets") Integer noOfTickets,
                                               @Param("fareConditions") String fareConditions);

    /**
     * <a href="file:src/main/resources/db/migration/V3__add_check_free_seats_func.sql"/> Func code
     */
    @Query(value = "SELECT check_free_seats(:flightId, :noOfTickets, :aircraftCode, :fareConditions)", nativeQuery = true)
    Boolean checkFreeSeats(@Param("flightId") Integer flightId,
                           @Param("noOfTickets") Integer noOfTickets,
                           @Param("aircraftCode") String aircraftCode,
                           @Param("fareConditions") String fareConditions);

    @Query(value = "SELECT * FROM flights AS fl " +
            "WHERE departure_airport =:from " +
            "AND arrival_airport =:to " +
            "AND scheduled_departure BETWEEN :dayStart AND :dayEnd " +
            "AND status IN ('Scheduled', 'On Time', 'Delayed') " +
            "AND check_free_seats(fl.flight_id, :noOfTickets, fl.aircraft_code, :fareConditions) " +
            "ORDER BY scheduled_departure", nativeQuery = true)
    List<Flights> findDefaultDirFlights(@Param("from") String from,
                                        @Param("to") String to,
                                        @Param("dayStart") LocalDateTime dayStart,
                                        @Param("dayEnd") LocalDateTime dayEnd,
                                        @Param("noOfTickets") Integer noOfTickets,
                                        @Param("fareConditions") String fareConditions);

    @Query(value = "SELECT DISTINCT arrival_airport FROM flights " +
            "WHERE departure_airport =:first " +
            "INTERSECT " +
            "SELECT DISTINCT arrival_airport FROM flights " +
            "WHERE departure_airport =:second", nativeQuery = true)
    List<String> findIntersectingArrivalAirports(@Param("first") String first,
                                                 @Param("second") String second);

    @Query(value = "SELECT EXISTS" +
            "                      (SELECT * FROM flights " +
            "                       JOIN seats USING(aircraft_code) " +
            "                       WHERE flight_id =:flightId AND fare_conditions =:fareConditions)", nativeQuery = true)
    Boolean checkCompatibilityFlightAndFareConditions(@Param("flightId") Integer flightId,
                                                      @Param("fareConditions") String fareConditions);
}
