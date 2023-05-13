select * from (SELECT f1.flight_no, f1.class, avg(f1.price) from (select distinct
                                                                      fl.flight_no as flight_no,
                                                                      se.fare_conditions as class,
                                                                      bo.total_amount as price
                                                                  from boarding_passes bp
                                                                           inner join flights fl on fl.flight_id = bp.flight_id
                                                                           inner join seats se on se.aircraft_code = fl.aircraft_code and  bp.seat_no = se.seat_no
                                                                           inner join tickets ti on ti.ticket_no = bp.ticket_no
                                                                           inner join bookings bo on bo.book_ref = ti.book_ref
                                                                  GROUP BY bp.ticket_no, fl.flight_no, bp.seat_no, se.fare_conditions, bo.total_amount HAVING count(*) = 1) f1

               GROUP BY f1.flight_no, f1.class) f2 order by  f2.flight_no;
