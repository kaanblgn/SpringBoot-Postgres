package com.sekom.banking.dto.response;

import com.sekom.banking.enums.AccountStatus;
import com.sekom.banking.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountResponseDto {

    private UUID id;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private CreateAccountBankResponseDto bank;
    private CreateAccountUserResponseDto user;

}

