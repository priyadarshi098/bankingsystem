package com.bank.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bank.paymentservice.dto.AccountDto;

@FeignClient(name="account-service", path="/accounts")
public interface AccountServiceClient {

    @GetMapping("/loadaccount/{accountNumber}")
    public AccountDto loadAccount(@PathVariable Long accountNumber);

    @PutMapping("/update/{accountNumber}")
    public AccountDto updateAccount(
        @PathVariable long accountNumber, 
        @RequestBody AccountDto accountDto);

}
