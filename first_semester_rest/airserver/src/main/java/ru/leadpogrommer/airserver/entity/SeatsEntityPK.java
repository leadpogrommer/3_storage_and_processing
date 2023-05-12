package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class SeatsEntityPK implements Serializable {
    @Column(name = "aircraft_code", nullable = false, length = 3)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String aircraftCode;
    @Column(name = "seat_no", nullable = false, length = 4)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String seatNo;

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
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
        SeatsEntityPK that = (SeatsEntityPK) o;
        return Objects.equals(aircraftCode, that.aircraftCode) && Objects.equals(seatNo, that.seatNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftCode, seatNo);
    }
}
