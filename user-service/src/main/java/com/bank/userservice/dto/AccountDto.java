package com.bank.userservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class AccountDto {

    private Long accountNumber;

    private Long userId;

    private String accountType;

    private BigDecimal balance;

    private String currency;

    private String accountStatus;

    private String branchCode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
