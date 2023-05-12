package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "flights_v", schema = "bookings", catalog = "demo")
public class FlightsVEntity {
    @Basic
    @Id
    @Column(name = "flight_id", nullable = true)
    private Integer flightId;
    @Basic
    @Column(name = "flight_no", nullable = true, length = 6)
    private String flightNo;
    @Basic
    @Column(name = "scheduled_departure", nullable = true)
    private Object scheduledDeparture;
    @Basic
    @Column(name = "scheduled_departure_local", nullable = true)
    private Timestamp scheduledDepartureLocal;
    @Basic
    @Column(name = "scheduled_arrival", nullable = true)
    private Object scheduledArrival;
    @Basic
    @Column(name = "scheduled_arrival_local", nullable = true)
    private Timestamp scheduledArrivalLocal;
    @Basic
    @Column(name = "scheduled_duration", nullable = true)
    private Object scheduledDuration;
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
    @Column(name = "status", nullable = true, length = 20)
    private String status;
    @Basic
    @Column(name = "aircraft_code", nullable = true, length = 3)
    private String aircraftCode;
    @Basic
    @Column(name = "actual_departure", nullable = true)
    private Object actualDeparture;
    @Basic
    @Column(name = "actual_departure_local", nullable = true)
    private Timestamp actualDepartureLocal;
    @Basic
    @Column(name = "actual_arrival", nullable = true)
    private Object actualArrival;
    @Basic
    @Column(name = "actual_arrival_local", nullable = true)
    private Timestamp actualArrivalLocal;
    @Basic
    @Column(name = "actual_duration", nullable = true)
    private Object actualDuration;

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Object getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(Object scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public Timestamp getScheduledDepartureLocal() {
        return scheduledDepartureLocal;
    }

    public void setScheduledDepartureLocal(Timestamp scheduledDepartureLocal) {
        this.scheduledDepartureLocal = scheduledDepartureLocal;
    }

    public Object getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(Object scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public Timestamp getScheduledArrivalLocal() {
        return scheduledArrivalLocal;
    }

    public void setScheduledArrivalLocal(Timestamp scheduledArrivalLocal) {
        this.scheduledArrivalLocal = scheduledArrivalLocal;
    }

    public Object getScheduledDuration() {
        return scheduledDuration;
    }

    public void setScheduledDuration(Object scheduledDuration) {
        this.scheduledDuration = scheduledDuration;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public Object getActualDeparture() {
        return actualDeparture;
    }

    public void setActualDeparture(Object actualDeparture) {
        this.actualDeparture = actualDeparture;
    }

    public Timestamp getActualDepartureLocal() {
        return actualDepartureLocal;
    }

    public void setActualDepartureLocal(Timestamp actualDepartureLocal) {
        this.actualDepartureLocal = actualDepartureLocal;
    }

    public Object getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(Object actualArrival) {
        this.actualArrival = actualArrival;
    }

    public Timestamp getActualArrivalLocal() {
        return actualArrivalLocal;
    }

    public void setActualArrivalLocal(Timestamp actualArrivalLocal) {
        this.actualArrivalLocal = actualArrivalLocal;
    }

    public Object getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(Object actualDuration) {
        this.actualDuration = actualDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightsVEntity that = (FlightsVEntity) o;
        return Objects.equals(flightId, that.flightId) && Objects.equals(flightNo, that.flightNo) && Objects.equals(scheduledDeparture, that.scheduledDeparture) && Objects.equals(scheduledDepartureLocal, that.scheduledDepartureLocal) && Objects.equals(scheduledArrival, that.scheduledArrival) && Objects.equals(scheduledArrivalLocal, that.scheduledArrivalLocal) && Objects.equals(scheduledDuration, that.scheduledDuration) && Objects.equals(departureAirport, that.departureAirport) && Objects.equals(departureAirportName, that.departureAirportName) && Objects.equals(departureCity, that.departureCity) && Objects.equals(arrivalAirport, that.arrivalAirport) && Objects.equals(arrivalAirportName, that.arrivalAirportName) && Objects.equals(arrivalCity, that.arrivalCity) && Objects.equals(status, that.status) && Objects.equals(aircraftCode, that.aircraftCode) && Objects.equals(actualDeparture, that.actualDeparture) && Objects.equals(actualDepartureLocal, that.actualDepartureLocal) && Objects.equals(actualArrival, that.actualArrival) && Objects.equals(actualArrivalLocal, that.actualArrivalLocal) && Objects.equals(actualDuration, that.actualDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, flightNo, scheduledDeparture, scheduledDepartureLocal, scheduledArrival, scheduledArrivalLocal, scheduledDuration, departureAirport, departureAirportName, departureCity, arrivalAirport, arrivalAirportName, arrivalCity, status, aircraftCode, actualDeparture, actualDepartureLocal, actualArrival, actualArrivalLocal, actualDuration);
    }
}
