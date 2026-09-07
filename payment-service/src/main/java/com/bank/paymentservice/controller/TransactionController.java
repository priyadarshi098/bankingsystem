package com.bank.paymentservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.paymentservice.dto.TransactionDetailsDto;
import com.bank.paymentservice.service.TransactionServce;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TransactionController {

    private final TransactionServce txnservice;

    @PostMapping("/pay")
    public ResponseEntity<TransactionDetailsDto> pay(@RequestBody TransactionDetailsDto txnDto){
        return ResponseEntity.ok(txnservice.pay(txnDto));
    }
}
