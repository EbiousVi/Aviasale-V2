package com.ebious.aviasale.domain.apiDto.req;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class PassengerDto {
    @NotNull
    @Size(min = 5, max = 64)
    private String fullName;

    @NotNull
    @Size(min = 11, max = 11)
    private String passengerId;

    @NotNull
    @Size(min = 10, max = 15)
    private String phoneNo;

    @NotNull
    @Size(min = 6, max = 64)
    private String email;

    public PassengerDto() {
    }

    public PassengerDto(String fullName, String passengerId, String phoneNo, String email) {
        this.fullName = fullName;
        this.passengerId = passengerId;
        this.phoneNo = phoneNo;
        this.email = email;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PassengerDto{" +
                "fullName='" + fullName + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
