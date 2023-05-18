package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class BoardingPassesEntityPK implements Serializable {
    @Column(name = "ticket_no", nullable = false, length = 13)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ticketNo;
    @Column(name = "flight_id", nullable = false)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardingPassesEntityPK that = (BoardingPassesEntityPK) o;
        return flightId == that.flightId && Objects.equals(ticketNo, that.ticketNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNo, flightId);
    }
}
