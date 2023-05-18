-- drop view if exists routes CASCADE ;
DROP table if exists cities cascade ;

CREATE table cities (
                        id serial primary key,
                        city text
);

insert into cities (city) SELECT distinct city from airports;


create or replace view routes
            (flight_no, departure_airport, departure_airport_name, departure_city, arrival_airport,
             arrival_airport_name, arrival_city, aircraft_code, duration, days_of_week, departure, arrival)
as
WITH f3 AS (SELECT f2.flight_no,
                   f2.departure_airport,
                   f2.arrival_airport,
                   f2.aircraft_code,
                   f2.duration,
                   array_agg(f2.days_of_week) AS days_of_week,
                   f2.departure,
                   f2.arrival
            FROM (SELECT f1.flight_no,
                         f1.departure_airport,
                         f1.arrival_airport,
                         f1.aircraft_code,
                         f1.duration,
                         f1.days_of_week,
                         f1.departure,
                         f1.arrival
                  FROM (SELECT flights.flight_no,
                               flights.departure_airport,
                               flights.arrival_airport,
                               flights.aircraft_code,
                               flights.scheduled_arrival - flights.scheduled_departure   AS duration,
                               to_char(flights.scheduled_departure, 'ID'::text)::integer AS days_of_week,
                                flights.scheduled_departure::time as departure,
                                flights.scheduled_arrival::time AS arrival
                        FROM flights) f1
                  GROUP BY f1.flight_no, f1.departure_airport, f1.arrival_airport, f1.aircraft_code, f1.duration,
                           f1.days_of_week, departure, arrival
                  ORDER BY f1.flight_no, f1.departure_airport, f1.arrival_airport, f1.aircraft_code, f1.duration,
                           f1.days_of_week) f2
            GROUP BY f2.flight_no, f2.departure_airport, f2.arrival_airport, f2.aircraft_code, f2.duration, f2.departure, f2.arrival)
SELECT f3.flight_no,
       f3.departure_airport,
       dep.airport_name AS departure_airport_name,
       dep.city         AS departure_city,
       f3.arrival_airport,
       arr.airport_name AS arrival_airport_name,
       arr.city         AS arrival_city,
       f3.aircraft_code,
       f3.duration,
       f3.days_of_week,
       f3.departure,
       f3.arrival
FROM f3,
     airports dep,
     airports arr
WHERE f3.departure_airport = dep.airport_code
  AND f3.arrival_airport = arr.airport_code;

comment on view routes is 'Routes';

comment on column routes.flight_no is 'Flight number';

comment on column routes.departure_airport is 'Code of airport of departure';

comment on column routes.departure_airport_name is 'Name of airport of departure';

comment on column routes.departure_city is 'City of departure';

comment on column routes.arrival_airport is 'Code of airport of arrival';

comment on column routes.arrival_airport_name is 'Name of airport of arrival';

comment on column routes.arrival_city is 'City of arrival';

comment on column routes.aircraft_code is 'Aircraft code, IATA';

comment on column routes.duration is 'Scheduled duration of flight';

comment on column routes.days_of_week is 'Days of week on which flights are scheduled';

comment on column routes.departure is 'Departure time';

comment on column routes.arrival is 'Arrival time';



create or replace view airports(airport_code, airport_name, city, coordinates, timezone) as
SELECT ml.airport_code,
       ml.airport_name ->> lang() AS airport_name,
       ml.city ->> lang()         AS city,
       ml.coordinates,
       ml.timezone,
       ((ascii(right(ml.airport_code, 3))-65) + (ascii(right(ml.airport_code, 2))-65)*26 + (ascii(right(ml.airport_code, 1))-65)*26*26) as num_code
FROM airports_data ml;


create or replace view path_help(departure_airport, arrival_airport, id_departure, id_arrival, departure_city, arrival_city, departure, id_route, stub_cost) as
SELECT
    r.departure_airport,
    r.arrival_airport,
    a1.num_code as id_departure,
    a2.num_code as id_arrival,
    r.departure_city,
    r.arrival_city,
    r.departure,
    ((a1.num_code + a2.num_code) * (a1.num_code + a2.num_code + 1) / 2 + a2.num_code) as id_route,
    v.stub_cost
FROM routes r
         LEFT JOIN airports a1 on a1.airport_code = r.departure_airport
         LEFT JOIN airports a2 on a2.airport_code = r.arrival_airport
         left join (values (1)) as v(stub_cost) on true
;
-- drop view if exists prices;
drop materialized view if exists prices;
create materialized view prices AS
WITH
    flights_seat_prices as (
        SELECT
            bp.flight_id as flight_id,
            plane.fare_conditions as fare_conditions,
            plane.aircraft_code as plane_code,
            bp.seat_no as seat_no,
            tf.amount as amount,
            route.flight_no as flight_no
        FROM flights flight
                 INNER JOIN (SELECT aircraft_code, fare_conditions FROM seats
                             GROUP BY aircraft_code, fare_conditions) plane ON plane.aircraft_code = flight.aircraft_code
                 INNER JOIN ticket_flights tf ON tf.flight_id = flight.flight_id and tf.fare_conditions = plane.fare_conditions
                 INNER JOIN boarding_passes bp on tf.ticket_no = bp.ticket_no and bp.flight_id = flight.flight_id
                 INNER JOIN routes route on route.flight_no = flight.flight_no
    ) select flight_no, plane_code, fare_conditions,percentile_cont(0.5) within group ( order by amount ) from flights_seat_prices

group by fare_conditions, plane_code, flight_no