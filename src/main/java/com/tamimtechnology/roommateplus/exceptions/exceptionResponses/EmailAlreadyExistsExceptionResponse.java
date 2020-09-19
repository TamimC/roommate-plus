package com.tamimtechnology.roommateplus.exceptions.exceptionResponses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAlreadyExistsExceptionResponse {
    private String emailAlreadyExists;
    public EmailAlreadyExistsExceptionResponse(String emailAlreadyExists) {
        this.emailAlreadyExists = emailAlreadyExists;
    }
}
