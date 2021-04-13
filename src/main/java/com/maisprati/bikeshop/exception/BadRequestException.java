package com.maisprati.bikeshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public static final String MESSAGE_ID = "Error %s because there is no record with the ID %s.";

    public BadRequestException(String action, Long id) {
        super(String.format(MESSAGE_ID, action, id));
    }

    public BadRequestException(String message) {
        super(message);
    }
}
