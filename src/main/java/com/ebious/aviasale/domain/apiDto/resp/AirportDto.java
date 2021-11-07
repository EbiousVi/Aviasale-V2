package com.ebious.aviasale.domain.apiDto.resp;

public class AirportDto {
    public String code;
    public String city;
    public String name;

    public AirportDto() {
    }

    public AirportDto(String code, String city, String name) {
        this.code = code;
        this.city = city;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AirportDto{" +
                "code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
