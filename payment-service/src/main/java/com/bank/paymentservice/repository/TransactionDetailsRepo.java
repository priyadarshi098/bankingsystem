package com.bank.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.paymentservice.entity.TransactionDetails;

@Repository
public interface TransactionDetailsRepo extends JpaRepository<TransactionDetails, Long>{

}
