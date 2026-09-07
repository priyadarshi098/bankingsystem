package com.bank.accountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountservice.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

    public Optional<Account> findByUserId(Long userId);
    
    public boolean existsByUserId(Long userId);

}
