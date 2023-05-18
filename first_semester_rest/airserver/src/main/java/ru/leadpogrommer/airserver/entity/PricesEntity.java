package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "prices", schema = "bookings", catalog = "demo")
public class PricesEntity {
    @Basic
    @Id
    @Column(name = "flight_no")
    private String flightNo;
    @Basic
    @Column(name = "plane_code")
    private String planeCode;
    @Basic
    @Column(name = "fare_conditions")
    private String fareConditions;
    @Basic
    @Column(name = "percentile_cont")
    private Double price;

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getPlaneCode() {
        return planeCode;
    }

    public void setPlaneCode(String planeCode) {
        this.planeCode = planeCode;
    }

    public String getFareConditions() {
        return fareConditions;
    }

    public void setFareConditions(String fareConditions) {
        this.fareConditions = fareConditions;
    }

    public Double getprice() {
        return price;
    }

    public void setprice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricesEntity that = (PricesEntity) o;
        return Objects.equals(flightNo, that.flightNo) && Objects.equals(planeCode, that.planeCode) && Objects.equals(fareConditions, that.fareConditions) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNo, planeCode, fareConditions, price);
    }
}
