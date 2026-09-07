package com.bank.accountservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bank.accountservice.type.AccountStatus;
import com.bank.accountservice.type.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "accountDetails")
public class Account {

    @Id
    @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "account_number_seq")
    @SequenceGenerator(
    name = "account_number_seq",
    sequenceName = "account_number_sequence",
    initialValue = (int) 12345678901112L,
    allocationSize = 13)
    private Long accountNumber;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private BigDecimal balance;

    private String currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private String branchCode;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String referenceNumber;

    private BigDecimal debitAmount;

    private BigDecimal creditAmount;

}
