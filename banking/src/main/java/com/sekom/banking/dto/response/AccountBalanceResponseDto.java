package com.sekom.banking.dto.response;

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
public class AccountBalanceResponseDto {

    private UUID accountId;
    private BigDecimal balance;
    private LocalDateTime updatedAt;

}
