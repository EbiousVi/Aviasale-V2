package com.ebious.aviasale.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tickets")
@TypeDef(name = "json_binary", typeClass = com.vladmihalcea.hibernate.type.json.JsonBinaryType.class)
public class Tickets {

    @Id
    @Column(name = "ticket_no")
    private String ticketNo;

    @Column(name = "book_ref")
    private String bookRef;

    @Column(name = "passenger_id")
    private String passengerId;

    @Column(name = "passenger_name")
    private String fullName;

    @Column(name = "contact_data", columnDefinition = "jsonb")
    @Type(type = "json_binary")
    private String contactDataJsonb;

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TicketFlights> ticketFlights;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_ref", insertable = false, updatable = false)
    private Bookings booking;

    public Tickets() {
    }

    public Tickets(String ticketNo, String bookRef, String passengerId,
                   String fullName, String contactDataJsonb) {
        this.ticketNo = ticketNo;
        this.bookRef = bookRef;
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.contactDataJsonb = contactDataJsonb;
    }

    public Tickets(String ticketNo, String bookRef, String passengerId, String fullName, String contactDataJsonb,
                   List<TicketFlights> ticketFlights, Bookings booking) {
        this.ticketNo = ticketNo;
        this.bookRef = bookRef;
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.contactDataJsonb = contactDataJsonb;
        this.ticketFlights = ticketFlights;
        this.booking = booking;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactDataJsonb() {
        return contactDataJsonb;
    }

    public void setContactDataJsonb(String contactDataJsonb) {
        this.contactDataJsonb = contactDataJsonb;
    }

    public List<TicketFlights> getTicketFlights() {
        return ticketFlights;
    }

    public void setTicketFlights(List<TicketFlights> ticketFlights) {
        this.ticketFlights = ticketFlights;
    }

    public Bookings getBooking() {
        return booking;
    }

    public void setBooking(Bookings booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "ticketNo='" + ticketNo + '\'' +
                ", bookRef='" + bookRef + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contactDataJsonb='" + contactDataJsonb + '\'' +
                '}';
    }
}
