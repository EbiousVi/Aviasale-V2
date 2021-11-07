package com.ebious.aviasale.repository;

import com.ebious.aviasale.domain.entity.Seats;
import com.ebious.aviasale.domain.projection.IFreeSeats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Seats.SeatsCompositePK> {

    /**
     * Select all seats by conditions and join boarding passes table.
     * If seat number are equals then seat is taken.
     * If boarding_passes seat number is null therefore seat are free.
     */
    @Query(value = "SELECT seats.seat_no AS seatNo, " +
            "CASE" +
            "     WHEN seats.seat_no = boarding_passes.seat_no THEN false " +
            "     WHEN boarding_passes.seat_no is null THEN true " +
            "     END AS free " +
            "FROM seats " +
            "LEFT JOIN boarding_passes ON seats.seat_no = boarding_passes.seat_no " +
            "AND boarding_passes.flight_id =:flightId " +
            "WHERE seats.aircraft_code =:aircraftCode " +
            "AND seats.fare_conditions =:fareConditions " +
            "ORDER BY seats.seat_no ", nativeQuery = true)
    List<IFreeSeats> findAllSeatsAndCheckIsFree(@Param("flightId") Integer flightId,
                                                @Param("aircraftCode") String aircraftCode,
                                                @Param("fareConditions") String fareConditions);

    @Query(value = "SELECT COUNT(*) AS all_seats_by_conditions FROM seats " +
            "WHERE aircraft_code = (SELECT aircraft_code FROM flights " +
            "WHERE flight_id =:flightId) " +
            "AND fare_conditions =:fareConditions", nativeQuery = true)
    Optional<Integer> findAllSeatsByConditions(@Param("flightId") Integer flightId,
                                               @Param("fareConditions") String fareConditions);
}
