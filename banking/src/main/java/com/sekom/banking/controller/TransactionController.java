package com.sekom.banking.controller;

import com.sekom.banking.business.TransactionBusinessService;
import com.sekom.banking.dto.request.TransactionRequestDto;
import com.sekom.banking.dto.response.TransactionResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionBusinessService transactionBusinessService;

    @GetMapping("/{transactionId}")
    public TransactionResponseDto getTransactionById(@PathVariable @NotNull UUID transactionId) {
        return transactionBusinessService.getTransactionById(transactionId);
    }

    @GetMapping
    public List<TransactionResponseDto> getAllTransactions() {
        return transactionBusinessService.getAllTransactions();
    }

    @PostMapping
    public TransactionResponseDto createTransaction(@Valid @RequestBody TransactionRequestDto transactionRequestDto) {
        return transactionBusinessService.createTransaction(transactionRequestDto);
    }

    @PutMapping("/{transactionId}")
    public TransactionResponseDto updateTransaction(@PathVariable @NotNull UUID transactionId,
                                                     @Valid @RequestBody TransactionRequestDto transactionRequestDto) {
        return transactionBusinessService.updateTransaction(transactionId, transactionRequestDto);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable @NotNull UUID transactionId) {
        transactionBusinessService.deleteTransaction(transactionId);
    }


}
