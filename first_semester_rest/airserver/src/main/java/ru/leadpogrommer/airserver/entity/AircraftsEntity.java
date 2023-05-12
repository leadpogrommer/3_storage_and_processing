package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "aircrafts", schema = "bookings", catalog = "demo")
public class AircraftsEntity {
    @Basic
    @Id
    @Column(name = "aircraft_code", nullable = true, length = 3)
    private String aircraftCode;
    @Basic
    @Column(name = "model", nullable = true, length = -1)
    private String model;
    @Basic
    @Column(name = "range", nullable = true)
    private Integer range;

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AircraftsEntity that = (AircraftsEntity) o;
        return Objects.equals(aircraftCode, that.aircraftCode) && Objects.equals(model, that.model) && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftCode, model, range);
    }
}
