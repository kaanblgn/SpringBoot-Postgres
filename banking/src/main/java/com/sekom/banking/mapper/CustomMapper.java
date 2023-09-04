package com.sekom.banking.mapper;

import com.sekom.banking.dto.request.BankRequestDto;
import com.sekom.banking.dto.request.TransactionForOperationRequestDto;
import com.sekom.banking.dto.request.TransactionRequestDto;
import com.sekom.banking.dto.request.UserRequestDto;
import com.sekom.banking.dto.response.*;
import com.sekom.banking.entity.Account;
import com.sekom.banking.entity.Bank;
import com.sekom.banking.entity.Transaction;
import com.sekom.banking.entity.User;
import com.sekom.banking.enums.AccountStatus;
import com.sekom.banking.enums.AccountType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomMapper {

    public User mapToEntity(UserRequestDto requestDto) {
        User user = new User();
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setAddress(requestDto.getAddress());
        user.setIdentityType(requestDto.getIdentityType());
        user.setIdentityNo(requestDto.getIdentityNo());
        return user;
    }

    public UserResponseDto mapToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getDateCreated(),
                user.getDateUpdated(),
                user.getIdentityType(),
                user.getIdentityNo()
        );
    }

    public UserWithAccountResponseDto mapWithAccountToResponseDto(User user){
        return new UserWithAccountResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getDateCreated(),
                user.getDateUpdated(),
                user.getIdentityType(),
                user.getIdentityNo(),
                mapAccountsToAccountResponseDtos(user.getAccounts())
        );
    }

    public List<AccountForUserResponseDto> mapAccountsToAccountResponseDtos(List<Account> accounts) {
        return accounts.stream()
                .map(account -> new AccountForUserResponseDto(
                        account.getId(),
                        account.getBalance(),
                        account.getDateCreated(),
                        account.getAccountType(),
                        account.getAccountStatus(),
                        account.getBank().getName()
                ))
                .toList();
    }

    public User mapToUpdateEntity(User userToUpdate,UserRequestDto requestDto){
        userToUpdate.setName(requestDto.getName());
        userToUpdate.setEmail(requestDto.getEmail());
        userToUpdate.setPassword(requestDto.getPassword());
        userToUpdate.setPhoneNumber(requestDto.getPhoneNumber());
        userToUpdate.setAddress(requestDto.getAddress());
        userToUpdate.setIdentityType(requestDto.getIdentityType());
        userToUpdate.setIdentityNo(requestDto.getPhoneNumber());
        return userToUpdate;
    }

    public CreateAccountResponseDto mapToCreateAccountResponseDto(Account account) {
        return new CreateAccountResponseDto(
                account.getId(),
                AccountType.CHECKING,
                AccountStatus.ACTIVE,
                new CreateAccountBankResponseDto(
                        account.getBank().getId(),
                        account.getBank().getName()
                ),
                new CreateAccountUserResponseDto(
                        account.getUser().getId(),
                        account.getUser().getName(),
                        account.getUser().getEmail(),
                        account.getUser().getPhoneNumber(),
                        account.getUser().getAddress(),
                        account.getUser().getIdentityType(),
                        account.getUser().getIdentityNo()
                )
        );
    }

    public BankResponseDto mapToResponseDto(Bank bank){
        return new BankResponseDto(
                bank.getId(),
                bank.getName(),
                bank.getDateCreated()
        );
    }

    public Bank mapToEntity(BankRequestDto requestDto){

        Bank bank = new Bank();
        bank.setName(requestDto.getName());
        return bank;
    }

    public TransactionResponseDto mapToResponseDto(Transaction transaction){
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getAccount().getId()
        );
    }

    public Transaction mapToEntity(TransactionRequestDto requestDto){

        Transaction transaction = new Transaction();
        transaction.setTransactionType(requestDto.getTransactionType());
        transaction.setAmount(requestDto.getAmount());
        return transaction;
    }

    public Transaction mapToEntity(TransactionForOperationRequestDto requestDto){

        Transaction transaction = new Transaction();
        transaction.setTransactionType(requestDto.getTransactionType());
        transaction.setAmount(requestDto.getAmount());
        return transaction;
    }

    public AccountResponseDto mapToResponseDto(Account account){
        return new AccountResponseDto(
                account.getId(),
                account.getBalance(),
                account.getDateCreated(),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getUser().getId(),
                account.getUser().getName(),
                account.getBank().getId(),
                account.getBank().getName()
        );
    }

    public AccountBalanceResponseDto mapToBalanceResponseDto(Account account) {
        return new AccountBalanceResponseDto(
                account.getId(),
                account.getBalance(),
                account.getDateUpdated()
        );
    }

    public TransactionForOperationResponseDto mapToTransactionForDepositResponseDto(Account account, Transaction transaction){
        return new TransactionForOperationResponseDto(
                transaction.getTransactionType(),
                transaction.getAmount(),
                account.getId(),
                account.getDateUpdated(),
                account.getBalance()
        );
    }

}
