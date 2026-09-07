package com.bank.paymentservice.entity;

import com.bank.paymentservice.type.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
    generator = "account_number_seq")
    @SequenceGenerator(
    name = "account_number_seq",
    sequenceName = "account_number_sequence",
    initialValue = (int) 1111111111,
    allocationSize = 19)
    private Long transactionId;
    
    private Long fromAccount;
   
    private Long toAccount;
    
    private Long amount;
    
    private String currency;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(unique = true, nullable = false)
    private String referenceNumber;

}
