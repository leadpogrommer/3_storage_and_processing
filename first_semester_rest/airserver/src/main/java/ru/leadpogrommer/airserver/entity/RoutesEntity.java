package ru.leadpogrommer.airserver.entity;

import io.hypersistence.utils.hibernate.type.array.IntArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
//@TypeD
@Table(name = "routes", schema = "bookings", catalog = "demo")
public class RoutesEntity {
    @Basic
    @Id
    @Column(name = "flight_no", nullable = true, length = 6)
    private String flightNo;
    @Basic
    @Column(name = "departure_airport", nullable = true, length = 3)
    private String departureAirport;
    @Basic
    @Column(name = "departure_airport_name", nullable = true, length = -1)
    private String departureAirportName;
    @Basic
    @Column(name = "departure_city", nullable = true, length = -1)
    private String departureCity;
    @Basic
    @Column(name = "arrival_airport", nullable = true, length = 3)
    private String arrivalAirport;
    @Basic
    @Column(name = "arrival_airport_name", nullable = true, length = -1)
    private String arrivalAirportName;
    @Basic
    @Column(name = "arrival_city", nullable = true, length = -1)
    private String arrivalCity;
    @Basic
    @Column(name = "aircraft_code", nullable = true, length = 3)
    private String aircraftCode;
    @Basic
    @Column(name = "duration", nullable = true)
    private Object duration;
    @Basic
    @Type(IntArrayType.class)
    @Column(name = "days_of_week", nullable = true)
    private int[] daysOfWeek;
    @Basic
    @Column(name = "departure", nullable = true)
    private LocalTime departure;
    @Basic
    @Column(name = "arrival", nullable = true)
    private LocalTime arrival;

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
        this.duration = duration;
    }

    public int[] getDaysOfWeek() {
        return daysOfWeek;
    }

//    public void setDaysOfWeek(List<Integer> daysOfWeek) {
//        this.daysOfWeek = daysOfWeek;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutesEntity that = (RoutesEntity) o;
        return Objects.equals(flightNo, that.flightNo) && Objects.equals(departureAirport, that.departureAirport) && Objects.equals(departureAirportName, that.departureAirportName) && Objects.equals(departureCity, that.departureCity) && Objects.equals(arrivalAirport, that.arrivalAirport) && Objects.equals(arrivalAirportName, that.arrivalAirportName) && Objects.equals(arrivalCity, that.arrivalCity) && Objects.equals(aircraftCode, that.aircraftCode) && Objects.equals(duration, that.duration) && Objects.equals(daysOfWeek, that.daysOfWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNo, departureAirport, departureAirportName, departureCity, arrivalAirport, arrivalAirportName, arrivalCity, aircraftCode, duration, daysOfWeek);
    }

    public LocalTime getDeparture() {
        return departure;
    }

//    public void setDeparture(Time departure) {
//        this.departure = departure;
//    }

    public LocalTime getArrival() {
        return arrival;
    }

//    public void setArrival(Time arrival) {
//        this.arrival = arrival;
//    }
}
