package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tickets", schema = "bookings", catalog = "demo")
public class TicketsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ticket_no", nullable = false, length = 13)
    private String ticketNo;
    @Basic
    @Column(name = "book_ref", nullable = false, length = 6)
    private String bookRef;
    @Basic
    @Column(name = "passenger_id", nullable = false, length = 20)
    private String passengerId;
    @Basic
    @Column(name = "passenger_name", nullable = false, length = -1)
    private String passengerName;
    @Basic
    @Column(name = "contact_data", nullable = true)
    private Object contactData;

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getBookRef() {
        return bookRef;
    }

    public void setBookRef(String bookRef) {
        this.bookRef = bookRef;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Object getContactData() {
        return contactData;
    }

    public void setContactData(Object contactData) {
        this.contactData = contactData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketsEntity that = (TicketsEntity) o;
        return Objects.equals(ticketNo, that.ticketNo) && Objects.equals(bookRef, that.bookRef) && Objects.equals(passengerId, that.passengerId) && Objects.equals(passengerName, that.passengerName) && Objects.equals(contactData, that.contactData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNo, bookRef, passengerId, passengerName, contactData);
    }
}
