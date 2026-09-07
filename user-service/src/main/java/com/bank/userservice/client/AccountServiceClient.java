package com.bank.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bank.userservice.dto.AccountDto;

@FeignClient(name = "account-service", path = "/accounts")
public interface AccountServiceClient {

    @GetMapping("/fetchaccount/{id}")
    public AccountDto getAccDetails(@PathVariable Long id);

    @GetMapping("/checkbalance/{id}")
    public Long userBalance(@PathVariable long id);

    @PostMapping("/createaccount/{userId}")
    public AccountDto createAccount(
        @PathVariable Long userId, 
        @RequestBody AccountDto accountDto);

}
