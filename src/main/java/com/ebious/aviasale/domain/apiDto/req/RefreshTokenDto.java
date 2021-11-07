package com.ebious.aviasale.domain.apiDto.req;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public class RefreshTokenDto {

    @NotBlank
    private String refreshToken;

    public RefreshTokenDto() {

    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenDto{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
