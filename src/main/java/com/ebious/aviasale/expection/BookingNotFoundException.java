package com.ebious.aviasale.expection;

import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends CustomException {
    public BookingNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
