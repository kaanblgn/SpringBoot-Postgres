package com.sekom.banking.controller;

import com.sekom.banking.business.AccountBusinessService;
import com.sekom.banking.dto.request.AccountRequestDto;
import com.sekom.banking.dto.request.TransactionForOperationRequestDto;
import com.sekom.banking.dto.response.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountBusinessService businessService;

    @GetMapping("/{accountId}")
    public AccountResponseDto getAccountById(@PathVariable @NotNull UUID accountId) {
        return businessService.getAccountById(accountId);
    }

    @GetMapping
    public List<AccountResponseDto> getAllAccounts() {
        return businessService.getAllAccounts();
    }

    @PostMapping
    public CreateAccountResponseDto createAccount(@Valid @RequestBody AccountRequestDto accountRequestDto) {
        return businessService.createAccount(accountRequestDto);
    }

    @PutMapping("/{accountId}")
    public AccountResponseDto updateAccount(@PathVariable @NotNull UUID accountId,
                                            @Valid @RequestBody AccountRequestDto accountRequestDto)
    {
        return businessService.updateAccount(accountId, accountRequestDto);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable @NotNull UUID accountId) {
        businessService.deleteAccount(accountId);
    }

    @GetMapping("/{accountId}/balance")
    public AccountBalanceResponseDto getAccountBalance(@PathVariable @NotNull UUID accountId) {
        return businessService.getAccountBalance(accountId);
    }

    @GetMapping("/{accountId}/transactions")
    public List<TransactionResponseDto> getAccountTransactions(@PathVariable @NotNull UUID accountId) {
        return businessService.getAccountTransactions(accountId);

    }

    @PostMapping("/{accountId}/deposit")
    public TransactionForOperationResponseDto depositToAccount(@PathVariable @NotNull UUID accountId,
                                                               @RequestBody @Valid TransactionForOperationRequestDto transactionForOperationRequestDto) {
        return businessService.depositToAccount(accountId, transactionForOperationRequestDto);
    }

    @PostMapping("/{accountId}/withdraw")
    public TransactionForOperationResponseDto withdrawFromAccount(@PathVariable @NotNull UUID accountId,
                                                                  @RequestBody @Valid TransactionForOperationRequestDto transactionForOperationRequestDto) {
        return businessService.withdrawFromAccount(accountId, transactionForOperationRequestDto);
    }



}
