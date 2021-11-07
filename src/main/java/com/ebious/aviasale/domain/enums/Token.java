package com.ebious.aviasale.domain.enums;

public enum Token {
    ACCESS("accessToken"),
    REFRESH("refreshToken");

    public final String name;

    Token(String name) {
        this.name = name;
    }

}
