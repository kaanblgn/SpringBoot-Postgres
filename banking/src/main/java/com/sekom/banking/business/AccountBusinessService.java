package com.sekom.banking.business;

import com.sekom.banking.dto.request.AccountRequestDto;
import com.sekom.banking.dto.request.TransactionForOperationRequestDto;
import com.sekom.banking.dto.response.*;
import com.sekom.banking.entity.Account;
import com.sekom.banking.entity.Bank;
import com.sekom.banking.entity.Transaction;
import com.sekom.banking.entity.User;
import com.sekom.banking.enums.EntityType;
import com.sekom.banking.mapper.CustomMapper;
import com.sekom.banking.service.AccountService;
import com.sekom.banking.service.BankService;
import com.sekom.banking.service.TransactionService;
import com.sekom.banking.service.UserService;
import com.sekom.banking.service.impl.CrudException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountBusinessService {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final BankService bankService;
    private final UserService userService;
    private final CustomMapper mapper;

    private static final BigDecimal DAILY_LIMIT = new BigDecimal("10000.00");

    public AccountResponseDto getAccountById(UUID accountId) {
        return accountService.getAccountById(accountId)
                .map(mapper::mapToResponseDto)
                .orElseThrow(() ->  CrudException.entityNotFound(EntityType.ACCOUNT, String.valueOf(accountId)));
    }

    public List<AccountResponseDto> getAllAccounts() {
        List<Account> accountList = accountService.getAllAccounts();

        if (accountList.isEmpty()){
            throw CrudException.entitiesNotFound(EntityType.ACCOUNT);
        }

        return accountList.stream().map(mapper::mapToResponseDto).toList();
    }

    public CreateAccountResponseDto createAccount(AccountRequestDto requestDto) {

        User user = userService.getUserById(requestDto.getUserId()).orElseThrow(() ->  CrudException.entityNotFound(EntityType.USER, String.valueOf(requestDto.getUserId())));
        Bank bank = bankService.getBankById(requestDto.getBankId()).orElseThrow(() ->  CrudException.entityNotFound(EntityType.BANK, String.valueOf(requestDto.getBankId())));

        try {
            Account account = new Account();
            account.setUser(user);
            account.setBank(bank);
            Account createdAccount = accountService.createAccount(account);
            return mapper.mapToCreateAccountResponseDto(createdAccount);
        } catch (Exception e) {
            throw CrudException.createEntityFailed(EntityType.ACCOUNT, e);
        }
    }

    public AccountResponseDto updateAccount(UUID accountId, AccountRequestDto requestDto) {

        User user = userService.getUserById(requestDto.getUserId()).orElseThrow(() ->  CrudException.entityNotFound(EntityType.USER, String.valueOf(requestDto.getUserId())));
        Bank bank = bankService.getBankById(requestDto.getBankId()).orElseThrow(() ->  CrudException.entityNotFound(EntityType.BANK, String.valueOf(requestDto.getBankId())));
        Account accountToUpdate = accountService.getAccountById(accountId).orElseThrow(() -> CrudException.entityNotFound(EntityType.ACCOUNT, String.valueOf(accountId)));

        // early exit if bank id or user id from request dto is equal to accountToUpdate
        if (accountToUpdate.getBank().getId() == bank.getId() || accountToUpdate.getUser().getId() == bank.getId()){
            throw CrudException.updateEntityFailedDuplicated(EntityType.ACCOUNT, String.valueOf(accountId));
        }

        try {
            accountToUpdate.setUser(user);
            accountToUpdate.setBank(bank);
            Account updatedAccount = accountService.updateAccount(accountToUpdate);
            return mapper.mapToResponseDto(updatedAccount);
        } catch (Exception e){
            throw CrudException.updateEntityFailed(EntityType.ACCOUNT, String.valueOf(accountId), e);
        }
    }

    public void deleteAccount(UUID accountId) {
        Account accountToDelete = accountService.getAccountById(accountId)
                .orElseThrow(() -> CrudException.entityNotFound(EntityType.ACCOUNT, String.valueOf(accountId)));

        try {
            accountService.deleteAccount(accountToDelete);
        } catch (Exception e) {
            throw CrudException.deleteEntityFailed(EntityType.ACCOUNT, String.valueOf(accountId), e);
        }
    }

    public AccountBalanceResponseDto getAccountBalance(UUID accountId){
        return mapper.mapToBalanceResponseDto(accountService.getAccountBalance(accountId));
    }

    public List<TransactionResponseDto> getAccountTransactions(UUID accountId){

        List<Transaction> transactionList = transactionService.getAccountTransactions(accountId);

        if (transactionList.isEmpty()){
            throw CrudException.entitiesNotFound(EntityType.TRANSACTION);
        }

        return transactionList
                .stream()
                .map(mapper::mapToResponseDto)
                .toList();
    }

    public TransactionForOperationResponseDto depositToAccount(UUID accountId, TransactionForOperationRequestDto requestDto){

        Transaction transaction = mapper.mapToEntity(requestDto);

        BigDecimal transactionAmount = transaction.getAmount();

        if (transactionAmount == null || transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        Account depositedAccount = accountService.depositToAccount(accountId, transaction);

        return mapper.mapToTransactionForDepositResponseDto(depositedAccount, transaction);

    }

    public TransactionForOperationResponseDto withdrawFromAccount(UUID accountId, TransactionForOperationRequestDto requestDto){

        Transaction transaction = mapper.mapToEntity(requestDto);

        BigDecimal transactionAmount = transaction.getAmount();


        if (transactionAmount == null || transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        if (transactionAmount.compareTo(DAILY_LIMIT) > 0){
            throw new IllegalArgumentException("Daily deposit limit acceded.");
        }

        Account withdrawnAccount  = accountService.withdrawFromAccount(accountId, transaction);

        return mapper.mapToTransactionForDepositResponseDto(withdrawnAccount, transaction);
    }










}
