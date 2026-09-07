package com.bank.paymentservice.advice;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApiError {

    private HttpStatus status;

    private String message;

}
