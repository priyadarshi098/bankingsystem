package com.bank.paymentservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bank.paymentservice.client.AccountServiceClient;
import com.bank.paymentservice.dto.AccountDto;
import com.bank.paymentservice.dto.TransactionDetailsDto;
import com.bank.paymentservice.entity.TransactionDetails;
import com.bank.paymentservice.exception.LowBalanceException;
import com.bank.paymentservice.exception.TransactionNotFoundException;
import com.bank.paymentservice.repository.TransactionDetailsRepo;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServce {

    private final TransactionDetailsRepo txnRepo;
    private final ModelMapper modelMapper;
    private final AccountServiceClient accountServiceClient;

    public List<TransactionDetailsDto> fetchAllTxns(){
        return 
        txnRepo
        .findAll()
        .stream()
        .map(txn -> convertEntityToDto(txn))
        .collect(Collectors.toList());
    }

    public TransactionDetailsDto getTxnDetails(Long txnId){
        return 
        convertEntityToDto(
            txnRepo
            .findById(txnId)
            .orElseThrow(
                () -> new TransactionNotFoundException(
                    "No Transacton found with Given Txn id: "+txnId)));
    }

    @Transactional
    @Retry(name="paymentRetry", fallbackMethod = "payFallback")
    public TransactionDetailsDto pay(TransactionDetailsDto txnDto){

        Long fromAccount = txnDto.getFromAccount();
        Long toAccount = txnDto.getToAccount();
        BigDecimal amount = txnDto.getAmount();
        
        AccountDto senderAccountDto = 
        accountServiceClient.loadAccount(fromAccount);

        BigDecimal balance = senderAccountDto.getBalance();
        if(balance.compareTo(amount) < 0){
            throw new LowBalanceException(
                "insufficient balance in sender's account :"+fromAccount);
        }
        String reLong = String.valueOf(generaterefeLong());
        //TransactionDetails txn = txnRepo.save(convertDtoToEntity(txnDto));
        senderAccountDto.setBalance(balance.subtract(amount));
        senderAccountDto.setDebitAmount(amount);
        senderAccountDto.setReferenceNumber(reLong);
        accountServiceClient.updateAccount(fromAccount, senderAccountDto);
        AccountDto recieverAccountDto = accountServiceClient.loadAccount(toAccount);
        recieverAccountDto.setBalance(recieverAccountDto.getBalance().add(amount));
        recieverAccountDto.setCreditAmount(amount);
        recieverAccountDto.setReferenceNumber(reLong);
        accountServiceClient.updateAccount(toAccount, recieverAccountDto);
        txnDto.setReferenceNumber(reLong);
        TransactionDetails txn = txnRepo.save(convertDtoToEntity(txnDto));
        return convertEntityToDto(txn);
        
    }

    public TransactionDetailsDto payFallback(TransactionDetailsDto txnDto, Throwable throwable){
        System.out.println("fallback occured due to "+throwable.getMessage());
        return new TransactionDetailsDto();
    }

    private Long generaterefeLong(){
        return System.currentTimeMillis();
    }


    // private TransactionDetailsDto createTransaction(TransactionDetails txn){

    // }

    private TransactionDetailsDto convertEntityToDto(TransactionDetails txn){
        return 
        modelMapper
        .map(txn, TransactionDetailsDto.class);
    }

    private TransactionDetails convertDtoToEntity(TransactionDetailsDto txnDto){
        return 
        modelMapper
        .map(txnDto, TransactionDetails.class);
    }

}
