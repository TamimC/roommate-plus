package com.tamimtechnology.roommateplus.exceptions;

import com.tamimtechnology.roommateplus.exceptions.exceptionResponses.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public final ResponseEntity<Object> handleUsernameAlreadyExistsException (UsernameAlreadyExistsException exception, WebRequest request){
        UsernameAlreadyExistsResponse existsResponse = new UsernameAlreadyExistsResponse(exception.getMessage());
        return new ResponseEntity(existsResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public final ResponseEntity<Object> handleEmailAlreadyExistsException (EmailAlreadyExistsException exception, WebRequest request){
        EmailAlreadyExistsExceptionResponse existsResponse = new EmailAlreadyExistsExceptionResponse(exception.getMessage());
        return new ResponseEntity(existsResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomExpiredJwtException.class)
    public final ResponseEntity<Object> handleCustomExpiredJwtExceptionResponse (EmailAlreadyExistsException exception, WebRequest request){
        CustomExpiredJwtExceptionResponse existsResponse = new CustomExpiredJwtExceptionResponse(exception.getMessage());
        return new ResponseEntity(existsResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RequestHasNoRefreshTokenException.class)
    public final ResponseEntity<Object> handleRequestHasNoRefreshTokenException (RequestHasNoRefreshTokenException exception, WebRequest request){
        RequestHasNoRefreshTokenExceptionResponse existsResponse = new RequestHasNoRefreshTokenExceptionResponse(exception.getMessage());
        return new ResponseEntity(existsResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public final ResponseEntity<Object> handleInvalidRefreshTokenException (InvalidRefreshTokenException exception, WebRequest request){
        InvalidRefreshTokenExceptionResponse existsResponse = new InvalidRefreshTokenExceptionResponse(exception.getMessage());
        return new ResponseEntity(existsResponse, HttpStatus.UNAUTHORIZED);
    }
}
