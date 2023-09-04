package com.sekom.banking.integration;

import com.sekom.banking.entity.Account;
import com.sekom.banking.entity.Transaction;
import com.sekom.banking.enums.TransactionType;
import com.sekom.banking.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void testConcurrentDepositsAndWithdrawals() {
        // Create an account with an initial balance
        UUID accountId = UUID.fromString("1dd798a1-eb91-4ff7-a02b-4f1e1d9cfee8");
        BigDecimal initialBalance = new BigDecimal("2000.00");

        // Number of concurrent threads for deposit and withdrawal
        int numThreads = 10;

        // Amount to deposit and withdraw in each thread
        BigDecimal depositAmount = BigDecimal.valueOf(100);
        BigDecimal withdrawalAmount = BigDecimal.valueOf(50);

        // Create a thread pool for concurrent execution
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Create a list to hold future results
        List<Future<?>> futures = new ArrayList<>();

        // Concurrently deposit and withdraw from the account
        for (int i = 0; i < numThreads; i++) {
            futures.add(executorService.submit(() -> {
                log.info("Thread {} - Depositing some amount", Thread.currentThread().getId());
                // Deposit some amount
                Transaction depositTransaction = createTransaction(TransactionType.DEPOSIT, depositAmount);
                accountService.depositToAccount(accountId, depositTransaction);

                log.info("Thread {} - Withdrawing some amount", Thread.currentThread().getId());
                // Withdraw some amount
                Transaction withdrawalTransaction = createTransaction(TransactionType.WITHDRAW, withdrawalAmount);
                accountService.withdrawFromAccount(accountId, withdrawalTransaction);
            }));
        }

        // Wait for all threads to complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                log.error("Thread execution error", e);
            }
        }

        // Verify the final account balance
        Account updatedAccount = accountService.getAccountById(accountId).get();

        BigDecimal depositTotal = depositAmount.multiply(BigDecimal.valueOf(numThreads));
        BigDecimal withdrawalTotal = withdrawalAmount.multiply(BigDecimal.valueOf(numThreads));

        BigDecimal expectedBalance = initialBalance.add(depositTotal).subtract(withdrawalTotal);

        log.info("Expected Balance: {}", expectedBalance);
        log.info("Actual Balance:   {}", updatedAccount.getBalance());

        assertEquals(expectedBalance, updatedAccount.getBalance());
    }

    private Transaction createTransaction(TransactionType transactionType, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        // Set other transaction properties as needed
        return transaction;
    }
}
