CREATE FUNCTION check_free_seats(IN _flight_id integer,
								 IN _no_of_tickets integer, 
								 IN _aircraft_code varchar(3),
								 IN _fare_conditions varchar(10)) 
								 RETURNS boolean AS $$
DECLARE 
has_free_seats boolean;
BEGIN
IF 
EXISTS(SELECT * FROM seats
       WHERE aircraft_code = _aircraft_code
       AND fare_conditions = _fare_conditions)
AND
EXISTS(SELECT * FROM flights
	   WHERE aircraft_code = _aircraft_code
	   AND flight_id = _flight_id) 
THEN	  
     SELECT 
		   (
			(SELECT COUNT(*) AS all_seats_by_condition FROM seats
             WHERE aircraft_code = _aircraft_code
             AND fare_conditions = _fare_conditions
             GROUP BY fare_conditions)
		-
		     COALESCE(
                      (SELECT COUNT(*) AS reserved_seats_by_condition FROM ticket_flights
                       WHERE flight_id = _flight_id AND fare_conditions = _fare_conditions
                       GROUP BY fare_conditions), 0
				     )	   
		    ) >= _no_of_tickets INTO has_free_seats;
ELSE 
has_free_seats = false;
END IF;
RETURN has_free_seats;
END;
$$ LANGUAGE plpgsql;
