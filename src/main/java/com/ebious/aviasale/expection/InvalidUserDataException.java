package com.ebious.aviasale.expection;

import org.springframework.http.HttpStatus;

public class InvalidUserDataException extends CustomException {
    public InvalidUserDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
