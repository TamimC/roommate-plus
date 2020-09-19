package com.tamimtechnology.roommateplus.exceptions.exceptionResponses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomExpiredJwtExceptionResponse {
    private String ExpiredJwt;
    public CustomExpiredJwtExceptionResponse(String ExpiredJwt) {
        this.ExpiredJwt = ExpiredJwt;
    }
}