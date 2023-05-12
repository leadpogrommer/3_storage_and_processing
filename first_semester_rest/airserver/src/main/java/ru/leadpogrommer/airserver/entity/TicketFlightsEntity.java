package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ticket_flights", schema = "bookings", catalog = "demo")
@IdClass(TicketFlightsEntityPK.class)
public class TicketFlightsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ticket_no", nullable = false, length = 13)
    private String ticketNo;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "flight_id", nullable = false)
    private int flightId;
    @Basic
    @Column(name = "fare_conditions", nullable = false, length = 10)
    private String fareConditions;
    @Basic
    @Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketFlightsEntity that = (TicketFlightsEntity) o;
        return flightId == that.flightId && Objects.equals(ticketNo, that.ticketNo) && Objects.equals(fareConditions, that.fareConditions) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNo, flightId, fareConditions, amount);
    }
}
