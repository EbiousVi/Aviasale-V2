package com.ebious.aviasale.domain.entity;

import com.ebious.aviasale.domain.hibernateType.PointType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Table(name = "airports_data")
@TypeDefs({
        @TypeDef(name = "json_binary", typeClass = com.vladmihalcea.hibernate.type.json.JsonBinaryType.class),
        @TypeDef(name = "point_type", typeClass = PointType.class)
})
public class AirportsData {

    @Id
    @Column(name = "airport_code")
    private String airportCode;

    @Column(name = "airport_name", columnDefinition = "jsonb")
    @Type(type = "json_binary")
    private String airportName;

    @Column(name = "city", columnDefinition = "jsonb")
    @Type(type = "json_binary")
    private String city;

    @JsonIgnore
    @Column(name = "coordinates")
    @Type(type = "point_type")
    private Point point;

    @Column(name = "timezone")
    private String timeZone;

    public AirportsData() {
    }

    public AirportsData(String airportCode, String airportName, String city, String timeZone) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
        this.timeZone = timeZone;
    }

    public AirportsData(String airportCode, String airportName, String city, Point point, String timeZone) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.city = city;
        this.point = point;
        this.timeZone = timeZone;
    }

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

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "AirportsData{" +
                "airportCode='" + airportCode + '\'' +
                ", airportName='" + airportName + '\'' +
                ", city='" + city + '\'' +
                ", point=" + point +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
