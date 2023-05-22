WITH
    flights_seat_prices as (
        SELECT
            bp.flight_id as flight_id,
            plane.fare_conditions as fare_conditions,
            plane.aircraft_code as plane_code,
            bp.seat_no as seat_no,
            tf.amount as amount,
            route.flight_no as flight_no,
            bp.seat_no as seat
        FROM flights flight
        INNER JOIN (SELECT aircraft_code, fare_conditions FROM seats
                    GROUP BY aircraft_code, fare_conditions) plane ON plane.aircraft_code = flight.aircraft_code
        INNER JOIN ticket_flights tf ON tf.flight_id = flight.flight_id
        INNER JOIN boarding_passes bp on tf.ticket_no = bp.ticket_no and bp.flight_id = flight.flight_id
        INNER JOIN routes route on route.flight_no = flight.flight_no
    ) select flight_no, plane_code, fare_conditions, seat, percentile_cont(0.5) within group ( order by amount ), min(amount), max(amount) from flights_seat_prices

group by fare_conditions, plane_code, seat, flight_no