package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "boarding_passes", schema = "bookings", catalog = "demo")
@IdClass(BoardingPassesEntityPK.class)
public class BoardingPassesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ticket_no", nullable = false, length = 13)
    private String ticketNo;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "flight_id", nullable = false)
    private int flightId;
    @Basic
    @Column(name = "boarding_no", nullable = false)
    private int boardingNo;
    @Basic
    @Column(name = "seat_no", nullable = false, length = 4)
    private String seatNo;

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

    public int getBoardingNo() {
        return boardingNo;
    }

    public void setBoardingNo(int boardingNo) {
        this.boardingNo = boardingNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardingPassesEntity that = (BoardingPassesEntity) o;
        return flightId == that.flightId && boardingNo == that.boardingNo && Objects.equals(ticketNo, that.ticketNo) && Objects.equals(seatNo, that.seatNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNo, flightId, boardingNo, seatNo);
    }
}
