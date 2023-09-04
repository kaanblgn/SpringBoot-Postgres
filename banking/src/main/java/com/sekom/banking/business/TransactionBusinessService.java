package com.sekom.banking.business;

import com.sekom.banking.dto.request.TransactionRequestDto;
import com.sekom.banking.dto.response.TransactionResponseDto;
import com.sekom.banking.entity.Account;
import com.sekom.banking.entity.Transaction;
import com.sekom.banking.enums.EntityType;
import com.sekom.banking.mapper.CustomMapper;
import com.sekom.banking.service.AccountService;
import com.sekom.banking.service.TransactionService;
import com.sekom.banking.service.impl.CrudException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionBusinessService {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CustomMapper mapper;

    public TransactionResponseDto getTransactionById(UUID transactionId) {
        return transactionService.getTransactionById(transactionId)
                .map(mapper::mapToResponseDto)
                .orElseThrow(() ->  CrudException.entityNotFound(EntityType.TRANSACTION, String.valueOf(transactionId)));
    }

    public List<TransactionResponseDto> getAllTransactions() {
        List<Transaction> transactionList = transactionService.getAllTransactions();

        if (transactionList.isEmpty()){
            throw CrudException.entitiesNotFound(EntityType.TRANSACTION);
        }

        return transactionList.stream().map(mapper::mapToResponseDto).toList();
    }

    public TransactionResponseDto createTransaction(TransactionRequestDto requestDto) {

        Account account = accountService.getAccountById(requestDto.getAccountId())
                .orElseThrow(() ->  CrudException.entityNotFound(EntityType.ACCOUNT, String.valueOf(requestDto.getAccountId())));


        try {
            Transaction transaction = mapper.mapToEntity(requestDto);
            transaction.setAccount(account);
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return mapper.mapToResponseDto(createdTransaction);
        } catch (Exception e) {
            throw CrudException.createEntityFailed(EntityType.TRANSACTION, e);
        }
    }

    public TransactionResponseDto updateTransaction(UUID transactionId, TransactionRequestDto requestDto) {
        Transaction transactionToUpdate = transactionService.getTransactionById(transactionId)
                .orElseThrow(() -> CrudException.entityNotFound(EntityType.TRANSACTION, String.valueOf(transactionId)));

        transactionToUpdate.setTransactionType(requestDto.getTransactionType());
        transactionToUpdate.setAmount(requestDto.getAmount());

        try {
            Transaction updatedTransaction = transactionService.updateTransaction(transactionToUpdate);
            return mapper.mapToResponseDto(updatedTransaction);
        } catch (Exception e){
            throw CrudException.updateEntityFailed(EntityType.TRANSACTION, String.valueOf(transactionId), e);
        }
    }

    public void deleteTransaction(UUID transactionId) {
        Transaction transactionToDelete = transactionService.getTransactionById(transactionId)
                .orElseThrow(() -> CrudException.entityNotFound(EntityType.TRANSACTION, String.valueOf(transactionId)));

        try {
            transactionService.deleteTransaction(transactionToDelete);
        } catch (Exception e) {
            throw CrudException.deleteEntityFailed(EntityType.TRANSACTION, String.valueOf(transactionId), e);
        }
    }


}
