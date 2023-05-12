package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "airports_data", schema = "bookings", catalog = "demo")
public class AirportsDataEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "airport_code", nullable = false, length = 3)
    private String airportCode;
    @Basic
    @Column(name = "airport_name", nullable = false)
    private Object airportName;
    @Basic
    @Column(name = "city", nullable = false)
    private Object city;
    @Basic
    @Column(name = "coordinates", nullable = false)
    private Object coordinates;
    @Basic
    @Column(name = "timezone", nullable = false, length = -1)
    private String timezone;

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public Object getAirportName() {
        return airportName;
    }

    public void setAirportName(Object airportName) {
        this.airportName = airportName;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
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
        AirportsDataEntity that = (AirportsDataEntity) o;
        return Objects.equals(airportCode, that.airportCode) && Objects.equals(airportName, that.airportName) && Objects.equals(city, that.city) && Objects.equals(coordinates, that.coordinates) && Objects.equals(timezone, that.timezone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportCode, airportName, city, coordinates, timezone);
    }
}
