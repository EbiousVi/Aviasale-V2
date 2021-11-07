package com.ebious.aviasale.domain.apiDto.resp;

public class UserDto {
    private String email;
    private String fullName;
    private String passengerId;
    private String phoneNo;
    private String accessToken;
    private String refreshToken;

    public UserDto() {
    }

    public UserDto(String email, String fullName, String passengerId,
                   String phoneNo, String accessToken, String refreshToken) {
        this.email = email;
        this.fullName = fullName;
        this.passengerId = passengerId;
        this.phoneNo = phoneNo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
