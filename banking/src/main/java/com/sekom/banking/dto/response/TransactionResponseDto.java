package com.sekom.banking.dto.response;

import com.sekom.banking.enums.TransactionType;
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
public class TransactionResponseDto {

    private UUID id;
    private TransactionType transactionType;
    private LocalDateTime date;
    private BigDecimal amount;
    private UUID accountId;

}

