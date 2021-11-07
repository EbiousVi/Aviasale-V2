package com.ebious.aviasale.repository;

import com.ebious.aviasale.domain.entity.TicketFlights;
import com.ebious.aviasale.domain.entity.compositeKeys.TicketNoFlightIdPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TicketFlightsRepository extends JpaRepository<TicketFlights, TicketNoFlightIdPK> {
    /**
     * The booking engine only relies on the ticket_flights table.
     * The table may not contain records with flights where status = Scheduled,
     * therefore, no tickets have been booked. In this case, the request will return -1.
     * Each aircraft model has only one cabin layout. First, find all seats by aircraft
     * and fare conditions. After find all reserved seats by flight id and fare conditions.
     * And calculate the difference.
     */
    @Query(value = "SELECT" +
            "         CASE" +
            "           WHEN reserved_seats > 0 THEN" +
            "             CAST(" +
            "                  (SELECT(" +
            "                          (SELECT COUNT(*) AS all_seats_by_condition FROM seats" +
            "                            WHERE aircraft_code = (SELECT aircraft_code FROM flights " +
            "                                                   WHERE flight_id =:flightId)" +
            "                            AND fare_conditions =:fareConditions" +
            "                            GROUP BY fare_conditions)" +
            "                            - " +
            "                            reserved_seats" +
            "                          ) AS free_seats_by_condition " +
            "                  ) AS integer" +
            "             )" +
            "       WHEN reserved_seats = -1 THEN -1" +
            "       END AS free_seats_by_condition" +
            "       FROM COALESCE(" +
            "                       (SELECT count(*) FROM ticket_flights" +
            "                         WHERE flight_id =:flightId AND fare_conditions =:fareConditions" +
            "                         GROUP BY fare_conditions), -1" +
            "                    ) AS reserved_seats", nativeQuery = true)
    Optional<Integer> findFreeSeatsByFlight(@Param("flightId") Integer flightId,
                                            @Param("fareConditions") String fareConditions);

    /**
     * Database don't have information about flight cost. This query count avg cost
     * for already purchased tickets. And round to hundredths;
     */
    @Query(value = "SELECT round(avg(amount) - avg(amount)%100) AS avg_flight_cost FROM " +
            "(SELECT amount AS amount FROM flights " +
            "JOIN ticket_flights USING(flight_id) " +
            "WHERE flight_no =:flightNo and fare_conditions =:fareConditions " +
            "GROUP BY flight_no, amount, fare_conditions) " +
            "AS amount_by_flight_no_and_fare_conditions", nativeQuery = true)
    Optional<BigDecimal> avgFlightCostByFlightNoAndFareConditions(@Param("flightNo") String flightNo,
                                                                  @Param("fareConditions") String fareConditions);


/*    @Query(value = "SELECT * FROM ticket_flights " +
            "JOIN flights USING(flight_id) " +
            "WHERE ticket_no IN :listOfTicketNo " +
            "ORDER BY flights.scheduled_departure", nativeQuery = true)
    List<TicketFlights> findAllByListOfTicketNo(@Param("listOfTicketNo") List<String> listOfTicketNo);*/

    @Query(value = "SELECT * FROM ticket_flights " +
            "JOIN tickets USING(ticket_no) " +
            "JOIN bookings USING(book_ref) " +
            "WHERE bookings.book_ref =:bookRef", nativeQuery = true)
    List<TicketFlights> findAllByBookRef(@Param("bookRef") String bookRef);
}
