package com.tamimtechnology.roommateplus.exceptions.exceptionResponses;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameAlreadyExistsResponse {
    private String userNameAlreadyExists;

    public UsernameAlreadyExistsResponse(String userNameAlreadyExists){
        this.userNameAlreadyExists = userNameAlreadyExists;
    }
}
