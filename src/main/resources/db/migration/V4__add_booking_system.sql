CREATE FUNCTION check_free_seats_before_insert() RETURNS TRIGGER AS $$
BEGIN
PERFORM pg_advisory_xact_lock(NEW.flight_id);
IF  EXISTS(SELECT * FROM flights AS flight
		   WHERE flight_id = NEW.flight_id
		   AND EXISTS(SELECT * FROM seats
			          WHERE aircraft_code = flight.aircraft_code
				      AND fare_conditions = NEW.fare_conditions)
          )
THEN
    IF
	   (SELECT
      	      (
	           (SELECT COUNT(*) AS all_seats_by_condition FROM seats
	            WHERE aircraft_code = (SELECT aircraft_code FROM flights
		       						   WHERE flight_id = NEW.flight_id)
                AND fare_conditions = NEW.fare_conditions
                GROUP BY fare_conditions)
          -
               COALESCE(
                        (SELECT COUNT(*) AS reserved_seats_by_condition FROM ticket_flights
                         WHERE flight_id = NEW.flight_id
				         AND fare_conditions = NEW.fare_conditions
                         GROUP BY fare_conditions), 0)
	          )
       ) > 0
	THEN RETURN NEW;
	ELSE RAISE USING
	ERRCODE = 'P0001',
	MESSAGE = 'No free seats',
	DETAIL = json_build_object('flightId', NEW.flight_id, 'fareConditions', NEW.fare_conditions, 'ticketNo', NEW.ticket_no);
	END IF;
ELSE RAISE USING
ERRCODE = 'P0001',
MESSAGE = 'The Flight is incompatible with the fare conditions or Flight with flight id does not exist',
DETAIL = json_build_object('flightId', NEW.flight_id, 'fareConditions', NEW.fare_conditions, 'ticketNo', NEW.ticket_no);
END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TR_TicketFlights_BeforeInsert
BEFORE INSERT ON ticket_flights
FOR EACH ROW EXECUTE FUNCTION check_free_seats_before_insert();

