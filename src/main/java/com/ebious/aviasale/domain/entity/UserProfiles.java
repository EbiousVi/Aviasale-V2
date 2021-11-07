package com.ebious.aviasale.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_profiles")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class UserProfiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_full_name", nullable = false)
    private String fullName;

    @Column(name = "user_passport", unique = true)
    private String passengerId;

    @Type(type = "jsonb")
    @Column(name = "user_contact_data", columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private String contactDataJsonb;

    @JsonIgnore
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bookings> bookings;

    public UserProfiles() {
    }

    public UserProfiles(String fullName, String passengerId, String contactDataJsonb, List<Bookings> bookings) {
        this.fullName = fullName;
        this.passengerId = passengerId;
        this.contactDataJsonb = contactDataJsonb;
        this.bookings = bookings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getContactDataJsonb() {
        return contactDataJsonb;
    }

    public void setContactDataJsonb(String contactDataJsonb) {
        this.contactDataJsonb = contactDataJsonb;
    }

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "UserProfiles{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", contactDataJsonb='" + contactDataJsonb + '\'' +
                '}';
    }
}
