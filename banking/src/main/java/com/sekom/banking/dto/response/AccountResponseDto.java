package com.sekom.banking.dto.response;

import com.sekom.banking.enums.AccountStatus;
import com.sekom.banking.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {

    private UUID id;
    private BigDecimal balance;
    private LocalDateTime dateCreated;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private UUID userId;
    private String userName;
    private UUID bankId;
    private String bankName;

}

