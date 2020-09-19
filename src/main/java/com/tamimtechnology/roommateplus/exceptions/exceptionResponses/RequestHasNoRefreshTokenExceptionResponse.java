package com.tamimtechnology.roommateplus.exceptions.exceptionResponses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestHasNoRefreshTokenExceptionResponse {
    private String NoRefreshToken;
    public RequestHasNoRefreshTokenExceptionResponse(String NoRefreshToken) {
        this.NoRefreshToken = NoRefreshToken;
    }
}
