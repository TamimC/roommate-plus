package com.tamimtechnology.roommateplus.exceptions.exceptionResponses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class InvalidRefreshTokenExceptionResponse {
    private String NoRefreshToken;
    public InvalidRefreshTokenExceptionResponse(String NoRefreshToken) {
        this.NoRefreshToken = NoRefreshToken;
    }
}