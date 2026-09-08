package com.bank.accountservice.controller;

import java.math.BigDecimal;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.accountservice.dto.AccountDto;
import com.bank.accountservice.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final KafkaTemplate<NewTopic, String> kafkaTemplate;

    @Value("${kafka.topic.account-retrieved}")
    private String KAFKA_USER_ACCOUNT_RETRIEVED;

    @GetMapping("/fetchaccount/{userId}")
    public ResponseEntity<AccountDto> fetchAccountDetails(@PathVariable Long userId){

        
        // kafkaTemplate.send(KAFKA_USER_ACCOUNT_RETRIEVED, "the account has been retrieved for id: "+userId);
    
        // System.out.println("message=================================qued");
        return 
        ResponseEntity
        .ok(
            accountService
            .fetchByUserId(
                userId));

    }

    @GetMapping("/checkbalance/{userId}")
    public ResponseEntity<BigDecimal> fetchUserBalance(@PathVariable Long userId){
        return ResponseEntity.ok(accountService.checkBalance(userId));
    }

    @PostMapping("/createaccount/{userId}")
    public ResponseEntity<AccountDto> createAccount(
        @PathVariable Long userId, 
        @RequestBody AccountDto accountDto){
            return ResponseEntity.ok(accountService.addAccount(accountDto));
    }

    @GetMapping("/loadaccount/{id}") 
    public ResponseEntity<AccountDto> loadAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.loadById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, 
        @RequestBody AccountDto accountDto) {
            return ResponseEntity.ok(accountService.updateAllDataAccount(id, accountDto));
        }
}
