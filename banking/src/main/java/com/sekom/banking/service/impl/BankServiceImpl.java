package com.sekom.banking.service.impl;

import com.sekom.banking.entity.Bank;
import com.sekom.banking.repository.BankRepository;
import com.sekom.banking.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Override
    public Optional<Bank> getBankById(UUID bankId) {
        return bankRepository.findById(bankId);
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    @Transactional
    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    @Transactional
    public Bank updateBank(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    @Transactional
    public void deleteBank(Bank bank) {
        bankRepository.delete(bank);
    }
}
