package com.bank.accountservice.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.bank.accountservice.dto.AccountDto;
import com.bank.accountservice.entity.Account;
import com.bank.accountservice.exception.AccountNotFoundException;
import com.bank.accountservice.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;

    public BigDecimal checkBalance(Long userId) {
        return accountRepository
        .findByUserId(userId)
        .orElseThrow(
            ()-> new AccountNotFoundException(
                "account is not available for this account no: "+userId)).getBalance();
    }

    public AccountDto addAccount(AccountDto accountDto) {
        Account account = 
        accountRepository.save(convertDtoToEntity(accountDto));
        return convertEntityToDto(account);
    }

    public AccountDto updateAllDataAccount(Long accountNumber, AccountDto accountDto) {
        if(!accountRepository.existsById(accountNumber)){
            throw new AccountNotFoundException("Account is not present with id: "+accountNumber);
        }
        accountDto.setAccountNumber(accountNumber);
        Account account = 
        accountRepository.save(convertDtoToEntity(accountDto));
        return convertEntityToDto(account);
    }

    public AccountDto updatepartialDataAccount(Long accountNumber, Map<String, Object> details) {
         Account account = 
         accountRepository
         .findById(accountNumber)
         .orElseThrow(
            () -> new AccountNotFoundException("Account is not associated with id: "+accountNumber));

        details.forEach((key, value) -> {
            Field fieldToBeUpdated = 
            ReflectionUtils
            .getRequiredField(Account.class, key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, account, value);
        });

        return convertEntityToDto(accountRepository.save(account));
    }

    public void deleteAccount(long accountNumber) {
        if(accountRepository.existsById(accountNumber)){
            accountRepository.deleteById(accountNumber);
        }else{
            throw new AccountNotFoundException("account not found to be deleted");
        }

    }

    public List<AccountDto> fetchAllAccount() {
        List<Account> acclist = 
        accountRepository.findAll();
        return acclist
        .stream()
        .map(account -> convertEntityToDto(account))
        .collect(Collectors.toList());
    }

    public AccountDto fetchByUserId(Long userId) {
        return 
        convertEntityToDto(
            accountRepository
            .findByUserId(userId)
            .orElseThrow(
                ()-> new AccountNotFoundException("Account not found with id:" +userId)));
    }

    public AccountDto loadById(Long id){
        return 
        convertEntityToDto(
            accountRepository
            .findById(id)
            .orElseThrow(
                () -> new AccountNotFoundException(
                    "Account not found with given account number : "+id)));
    }

    private AccountDto convertEntityToDto(Account account){
        return 
        modelMapper
        .map(account, AccountDto.class);
    }

    private Account convertDtoToEntity(AccountDto accountDto){
        return 
        modelMapper
        .map(accountDto, Account.class);
    }

}
