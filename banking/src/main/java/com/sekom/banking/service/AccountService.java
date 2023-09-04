package com.sekom.banking.service;


import com.sekom.banking.entity.Account;
import com.sekom.banking.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {
    Optional<Account> getAccountById(UUID accountId);

    List<Account> getAllAccounts();

    Account createAccount(Account account);

    Account updateAccount(Account account);

    void deleteAccount(Account account);

    Account getAccountBalance(UUID accountId);

    Account depositToAccount(UUID accountId,
                             Transaction transaction);

    Account withdrawFromAccount(UUID accountId,
                             Transaction transaction);

}

