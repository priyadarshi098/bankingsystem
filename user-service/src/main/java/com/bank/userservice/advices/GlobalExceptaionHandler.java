package com.bank.userservice.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bank.userservice.exception.ResourceNotFoundException;
import com.bank.userservice.exception.UserAleradyRegisteredException;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptaionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception) {
        ApiError error = 
        ApiError
        .builder()
        .status(HttpStatus.NOT_FOUND)
        .message(exception.getMessage())
        .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAleradyRegisteredException.class) 
    public ResponseEntity<ApiError> handleUserExits(UserAleradyRegisteredException exception) {
        ApiError error = 
        ApiError
        .builder()
        .status(HttpStatus.BAD_REQUEST)
        .message(exception.getMessage())
        .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ApiError> handleFeignException(
        FeignException.NotFound exception) {

            ApiError error = 
            ApiError
            .builder()
            .status(HttpStatus.NOT_FOUND)
            .message(exception.getMessage())
            .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
