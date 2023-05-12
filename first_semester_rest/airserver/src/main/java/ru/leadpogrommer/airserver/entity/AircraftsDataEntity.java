package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "aircrafts_data", schema = "bookings", catalog = "demo")
public class AircraftsDataEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "aircraft_code", nullable = false, length = 3)
    private String aircraftCode;
    @Basic
    @Column(name = "model", nullable = false)
    private Object model;
    @Basic
    @Column(name = "range", nullable = false)
    private int range;

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AircraftsDataEntity that = (AircraftsDataEntity) o;
        return range == that.range && Objects.equals(aircraftCode, that.aircraftCode) && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftCode, model, range);
    }
}
