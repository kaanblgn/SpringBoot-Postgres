package com.sekom.banking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {

    @NotNull(message = "user id cannot be empty.")
    private UUID userId;
    @NotNull(message = "bank id cannot be empty.")
    private UUID bankId;

}

