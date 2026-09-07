package com.bank.userservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.bank.userservice.client.AccountServiceClient;
import com.bank.userservice.dto.AccountDto;
import com.bank.userservice.dto.UserDto;
import com.bank.userservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final AccountServiceClient accountServiceClient;


    @GetMapping("/userlist")
    public ResponseEntity<List<UserDto>> getUserList(){
        return ResponseEntity.ok(userService.getAllUserList());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> RegisterNewUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(
        @PathVariable Long id, 
        @RequestBody UserDto userDto) {

            return 
            ResponseEntity
            .ok(
                userService
                .updateAllDetailsUser(
                    id, 
                    userDto));
    }

    @PatchMapping("/modify/{id}")
    public ResponseEntity<UserDto> updateUserPartially(
        @PathVariable Long id, 
        @RequestBody Map<String, Object> details){

            return 
            ResponseEntity
            .ok(
                userService
                .updatePartialDetailsUser(
                    id, 
                    details));
    }

    @GetMapping("/acc/{id}")
    public ResponseEntity<AccountDto> getAccDetail(@PathVariable Long id){

        ServiceInstance accountService = 
        discoveryClient
        .getInstances(
            "account-service")
            .get(0);
            System.out.println("=========================================================================== "+accountService.getUri()+"/fetchaccount/"+id);
        AccountDto response = 
        restClient
        .get()
        .uri(accountService.getUri()+"/accounts/fetchaccount/"+id)
        .retrieve()
        .body(AccountDto.class);

        return ResponseEntity.ok(response);
        
     }

     @GetMapping("/accdetails/{id}")
    public ResponseEntity<AccountDto> getAccDetails(@PathVariable Long id){

        return ResponseEntity.ok(accountServiceClient.getAccDetails(id));
        
    }

    @GetMapping("/checkbalance/{id}")
    public ResponseEntity<Long> checkBalance(@PathVariable Long id){
        return ResponseEntity.ok(accountServiceClient.userBalance(id));
    }

    @PostMapping("/createacc/{id}")
    public ResponseEntity<AccountDto> createAccount(
        @PathVariable Long id, 
        @RequestBody AccountDto accountDto) {

            return 
            ResponseEntity
            .ok(
                accountServiceClient
                .createAccount(id, accountDto));
        }
}
