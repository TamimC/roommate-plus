package com.tamimtechnology.roommateplus.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private boolean success;
    private String token;
    private String refreshToken;
}
