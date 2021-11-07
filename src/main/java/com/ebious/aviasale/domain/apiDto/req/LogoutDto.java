package com.ebious.aviasale.domain.apiDto.req;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class LogoutDto {

    @NotNull
    @Size(min = 6, max = 64)
    private String email;

    public LogoutDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
