package com.tamimtechnology.roommateplus.controllers;

import com.tamimtechnology.roommateplus.model.BusinessEntities.User;
import com.tamimtechnology.roommateplus.payload.request.LoginRequest;
import com.tamimtechnology.roommateplus.services.ErrorValidationService;
import com.tamimtechnology.roommateplus.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@RestController
@RequestMapping("/api/users/**")
public class AuthController {

    private AuthService authService;
    private ErrorValidationService errorValidationService;

    public AuthController(AuthService authService, ErrorValidationService errorValidationService) {
        this.authService = authService;
        this.errorValidationService = errorValidationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody User userRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return errorValidationService.getErrorResponseMap(bindingResult);
        authService.signup(userRequest);
        return new ResponseEntity<>("The profile has been signed up!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return errorValidationService.getErrorResponseMap(bindingResult);
        return authService.login(loginRequest);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken (HttpServletRequest httpServletRequest){
        return authService.refresh(httpServletRequest);
    }
}
