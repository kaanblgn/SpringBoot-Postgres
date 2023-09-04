package com.sekom.banking.controller;

import com.sekom.banking.business.BankBusinessService;
import com.sekom.banking.dto.request.BankRequestDto;
import com.sekom.banking.dto.response.BankResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankBusinessService bankBusinessService;

    @GetMapping("/{userId}")
    public BankResponseDto getUserById(@PathVariable @NotNull UUID userId) {
        return bankBusinessService.getBankById(userId);
    }

    @GetMapping
    public List<BankResponseDto> getAllUsers() {
        return bankBusinessService.getAllBanks();
    }

    @PostMapping
    public BankResponseDto createUser(@Valid @RequestBody BankRequestDto userRequestDto) {
        return bankBusinessService.createBank(userRequestDto);
    }

    @PutMapping("/{userId}")
    public BankResponseDto updateUser(@PathVariable @NotNull UUID userId,
                                                     @Valid @RequestBody BankRequestDto userRequestDto)
    {
        return bankBusinessService.updateBank(userId, userRequestDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @NotNull UUID userId) {
        bankBusinessService.deleteBank(userId);
    }
}
