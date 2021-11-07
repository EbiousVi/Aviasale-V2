package com.ebious.aviasale.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Bookings {

    @Id
    @Column(name = "book_ref")
    private String bookRef;

    @Column(name = "book_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime bookDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfiles userProfile;

    @JsonIgnore
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<Tickets> tickets = new ArrayList<>();

    public Bookings() {
    }

    public Bookings(String bookRef, OffsetDateTime bookDate, BigDecimal totalAmount) {
        this.bookRef = bookRef;
        this.bookDate = bookDate;
        this.totalAmount = totalAmount;
    }

    public Bookings(String bookRef, OffsetDateTime bookDate, BigDecimal totalAmount, UserProfiles userProfile) {
        this.bookRef = bookRef;
        this.bookDate = bookDate;
        this.totalAmount = totalAmount;
        this.userProfile = userProfile;
    }

    public String getBookRef() {
        return bookRef;
    }

    public void setBookRef(String bookRef) {
        this.bookRef = bookRef;
    }

    public OffsetDateTime getBookDate() {
        return bookDate;
    }

    public void setBookDate(OffsetDateTime bookDate) {
        this.bookDate = bookDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public UserProfiles getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfiles userProfile) {
        this.userProfile = userProfile;
    }

    public List<Tickets> getTickets() {
        return tickets;
    }

    public void setTickets(List<Tickets> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Bookings{" +
                "bookRef='" + bookRef + '\'' +
                ", bookDate=" + bookDate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
