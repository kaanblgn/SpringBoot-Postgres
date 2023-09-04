package com.sekom.banking.dto.request;

import com.sekom.banking.enums.TransactionType;
import com.sekom.banking.validation.ValidTransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionForOperationRequestDto {

    @ValidTransactionType
    private TransactionType transactionType;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

}

