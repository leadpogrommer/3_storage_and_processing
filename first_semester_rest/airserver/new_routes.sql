drop view if exists routes;

create view routes
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

