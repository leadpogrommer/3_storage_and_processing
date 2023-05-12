package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "flights", schema = "bookings", catalog = "demo")
public class FlightsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "flight_id", nullable = false)
    private int flightId;
    @Basic
    @Column(name = "flight_no", nullable = false, length = 6)
    private String flightNo;
    @Basic
    @Column(name = "scheduled_departure", nullable = false)
    private Object scheduledDeparture;
    @Basic
    @Column(name = "scheduled_arrival", nullable = false)
    private Object scheduledArrival;
    @Basic
    @Column(name = "departure_airport", nullable = false, length = 3)
    private String departureAirport;
    @Basic
    @Column(name = "arrival_airport", nullable = false, length = 3)
    private String arrivalAirport;
    @Basic
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    @Basic
    @Column(name = "aircraft_code", nullable = false, length = 3)
    private String aircraftCode;
    @Basic
    @Column(name = "actual_departure", nullable = true)
    private Object actualDeparture;
    @Basic
    @Column(name = "actual_arrival", nullable = true)
    private Object actualArrival;

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
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

    public Object getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(Object scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
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

    public Object getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(Object actualArrival) {
        this.actualArrival = actualArrival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightsEntity that = (FlightsEntity) o;
        return flightId == that.flightId && Objects.equals(flightNo, that.flightNo) && Objects.equals(scheduledDeparture, that.scheduledDeparture) && Objects.equals(scheduledArrival, that.scheduledArrival) && Objects.equals(departureAirport, that.departureAirport) && Objects.equals(arrivalAirport, that.arrivalAirport) && Objects.equals(status, that.status) && Objects.equals(aircraftCode, that.aircraftCode) && Objects.equals(actualDeparture, that.actualDeparture) && Objects.equals(actualArrival, that.actualArrival);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, flightNo, scheduledDeparture, scheduledArrival, departureAirport, arrivalAirport, status, aircraftCode, actualDeparture, actualArrival);
    }
}
