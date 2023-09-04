package com.sekom.banking.service;

import com.sekom.banking.entity.Bank;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankService {

    Optional<Bank> getBankById(UUID bankId);
    List<Bank> getAllBanks();
    Bank createBank(Bank bank);
    Bank updateBank(Bank bank);
    void deleteBank(Bank bank);

}