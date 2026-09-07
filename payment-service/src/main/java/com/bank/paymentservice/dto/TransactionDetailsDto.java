package com.bank.paymentservice.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.bank.paymentservice.type.TransactionStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDetailsDto {

    private Long transactionId;
    
    private Long fromAccount;
   
    private Long toAccount;
    
    private BigDecimal amount;
    
    private String currency;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    
    private String referenceNumber; 

}
