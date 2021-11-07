package com.ebious.aviasale.expection;

import org.springframework.http.HttpStatus;

public class InternalEbiousAppErrorEx extends CustomException{
    public InternalEbiousAppErrorEx(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
