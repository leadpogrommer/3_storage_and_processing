package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "seats", schema = "bookings", catalog = "demo")
@IdClass(SeatsEntityPK.class)
public class SeatsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "aircraft_code", nullable = false, length = 3)
    private String aircraftCode;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "seat_no", nullable = false, length = 4)
    private String seatNo;
    @Basic
    @Column(name = "fare_conditions", nullable = false, length = 10)
    private String fareConditions;

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

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatsEntity that = (SeatsEntity) o;
        return Objects.equals(aircraftCode, that.aircraftCode) && Objects.equals(seatNo, that.seatNo) && Objects.equals(fareConditions, that.fareConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftCode, seatNo, fareConditions);
    }
}
