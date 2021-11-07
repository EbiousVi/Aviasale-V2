package com.ebious.aviasale.expection;

import org.springframework.http.HttpStatus;

public class TicketsNotFoundException extends CustomException {
    public TicketsNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
