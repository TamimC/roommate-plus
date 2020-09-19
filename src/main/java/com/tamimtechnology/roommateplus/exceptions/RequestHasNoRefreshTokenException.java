package com.tamimtechnology.roommateplus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RequestHasNoRefreshTokenException extends RuntimeException{
    public RequestHasNoRefreshTokenException(String message) {
        super(message);
    }
}
