package ru.leadpogrommer.airserver.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "bookings", schema = "bookings", catalog = "demo")
public class BookingsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_ref", nullable = false, length = 6)
    private String bookRef;
    @Basic
    @Column(name = "book_date", nullable = false)
    private Object bookDate;
    @Basic
    @Column(name = "total_amount", nullable = false, precision = 2)
    private BigDecimal totalAmount;

    public String getBookRef() {
        return bookRef;
    }

    public void setBookRef(String bookRef) {
        this.bookRef = bookRef;
    }

    public Object getBookDate() {
        return bookDate;
    }

    public void setBookDate(Object bookDate) {
        this.bookDate = bookDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingsEntity that = (BookingsEntity) o;
        return Objects.equals(bookRef, that.bookRef) && Objects.equals(bookDate, that.bookDate) && Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookRef, bookDate, totalAmount);
    }
}
