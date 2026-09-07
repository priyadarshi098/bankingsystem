package com.bank.accountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.bank.accountservice.type.AccountStatus;
import com.bank.accountservice.type.AccountType;

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
public class AccountDto {

    private Long accountNumber;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance;

    private String currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private String branchCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String referenceNumber;

    private BigDecimal debitAmount;

    private BigDecimal creditAmount;

}
