package com.bank.paymentservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bank.paymentservice.exception.LowBalanceException;
import com.bank.paymentservice.exception.TransactionNotFoundException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerAdvice {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiError> handleTxnNotFoundExp(
        TransactionNotFoundException exception) {

            ApiError error = 
            ApiError
            .builder()
            .status(HttpStatus.NOT_FOUND)
            .message(exception.getMessage())
            .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);  

    }

    @ExceptionHandler(LowBalanceException.class)
    public ResponseEntity<ApiError> handleLowBalanceExp(
        LowBalanceException exception) {

            ApiError error = 
            ApiError
            .builder()
            .status(HttpStatus.NOT_FOUND)
            .message(exception.getMessage())
            .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);  

    }

}
