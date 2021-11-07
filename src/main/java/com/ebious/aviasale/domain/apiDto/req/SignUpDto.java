package com.ebious.aviasale.domain.apiDto.req;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class SignUpDto {
    @NotNull
    @Size(min = 6, max = 64)
    private String email;

    @NotNull
    @Size(min = 6, max = 64)
    private String password;

    @NotNull
    @Size(min = 10, max = 15)
    private String phoneNo;

    @NotNull
    @Size(min = 5, max = 64)
    private String fullName;

    @NotNull
    @Size(min = 11, max = 11)
    private String passengerId;

    public SignUpDto() {

    }

    public SignUpDto(String email, String password, String phoneNo,
                     String fullName, String passengerId) {
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.fullName = fullName;
        this.passengerId = passengerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    @Override
    public String toString() {
        return "SignUpDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", fullName='" + fullName + '\'' +
                ", passengerId='" + passengerId + '\'' +
                '}';
    }
}
