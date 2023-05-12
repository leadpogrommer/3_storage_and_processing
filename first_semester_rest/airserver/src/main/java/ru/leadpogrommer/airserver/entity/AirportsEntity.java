package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "airports", schema = "bookings", catalog = "demo")
public class AirportsEntity {
    @Basic
    @Id
    @Column(name = "airport_code", nullable = true, length = 3)
    private String airportCode;
    @Basic
    @Column(name = "airport_name", nullable = true, length = -1)
    private String airportName;
    @Basic
    @Column(name = "city", nullable = true, length = -1)
    private String city;
    @Basic
    @Column(name = "coordinates", nullable = true)
    private Object coordinates;
    @Basic
    @Column(name = "timezone", nullable = true, length = -1)
    private String timezone;

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirportsEntity that = (AirportsEntity) o;
        return Objects.equals(airportCode, that.airportCode) && Objects.equals(airportName, that.airportName) && Objects.equals(city, that.city) && Objects.equals(coordinates, that.coordinates) && Objects.equals(timezone, that.timezone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportCode, airportName, city, coordinates, timezone);
    }
}
