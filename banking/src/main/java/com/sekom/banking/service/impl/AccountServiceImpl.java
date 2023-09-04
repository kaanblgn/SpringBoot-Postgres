package com.sekom.banking.service.impl;

import com.sekom.banking.entity.Account;
import com.sekom.banking.entity.Transaction;
import com.sekom.banking.enums.EntityType;
import com.sekom.banking.repository.AccountRepository;
import com.sekom.banking.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Optional<Account> getAccountById(UUID accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account updateAccount(Account accountToUpdate) {
        return accountRepository.save(accountToUpdate);
    }

    @Override
    @Transactional
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    @Override
    @Transactional
    public Account getAccountBalance(UUID accountId) {
        return accountRepository.findAccountWithPessimisticLock(accountId);
    }

    @Override
    @Transactional
    public Account depositToAccount(UUID accountId, Transaction transaction) {
        try {
            // Attempt to lock the account and perform the deposit operation
            Account lockedAccount = accountRepository.findAccountWithPessimisticLock(accountId);

            BigDecimal newBalance = lockedAccount.getBalance().add(transaction.getAmount());

            lockedAccount.setBalance(newBalance);
            lockedAccount.addTransaction(transaction);

            return accountRepository.save(lockedAccount);
        } catch (Exception ex) {
            throw CrudException.entityNotFound(EntityType.ACCOUNT, String.valueOf(accountId));
        }
    }

    @Override
    @Transactional
    public Account withdrawFromAccount(UUID accountId, Transaction transaction) {
        try {
            Account lockedAccount = accountRepository.findAccountWithPessimisticLock(accountId);
            BigDecimal currentBalance = lockedAccount.getBalance();
            BigDecimal withdrawalAmount = transaction.getAmount();

            if (currentBalance.compareTo(withdrawalAmount) < 0) {
                throw new IllegalArgumentException("Can not withdraw more than the current account balance");
            }

            BigDecimal newBalance = currentBalance.subtract(withdrawalAmount);

            lockedAccount.setBalance(newBalance);
            lockedAccount.addTransaction(transaction);

            return accountRepository.save(lockedAccount);
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw CrudException.entityNotFound(EntityType.ACCOUNT, String.valueOf(accountId));
        }
    }





}
