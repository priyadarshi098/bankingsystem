package com.bank.userservice.advices;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

    private HttpStatus status;

    private String message;

}
