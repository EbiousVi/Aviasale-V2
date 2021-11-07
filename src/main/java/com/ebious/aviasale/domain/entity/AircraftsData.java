package com.ebious.aviasale.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "aircrafts_data")
@TypeDef(name = "json_binary", typeClass = com.vladmihalcea.hibernate.type.json.JsonBinaryType.class)
public class AircraftsData {

    @Id
    @Column(name = "aircraft_code")
    private String aircraftCode;

    @Column(name = "model", columnDefinition = "jsonb")
    private String model;

    @Column(name = "range")
    private Integer range;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_code", insertable = false, updatable = false)
    private List<Seats> seats;

    public AircraftsData() {
    }

    public AircraftsData(String aircraftCode, String model, Integer range) {
        this.aircraftCode = aircraftCode;
        this.model = model;
        this.range = range;
    }

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

    public List<Seats> getSeats() {
        return seats;
    }

    public void setSeats(List<Seats> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "AircraftsData{" +
                "aircraftCode='" + aircraftCode + '\'' +
                ", model='" + model + '\'' +
                ", range=" + range +
                '}';
    }
}
