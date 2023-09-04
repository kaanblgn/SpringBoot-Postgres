package com.sekom.banking.controller;

import com.sekom.banking.business.UserBusinessService;
import com.sekom.banking.dto.request.UserRequestDto;
import com.sekom.banking.dto.response.UserResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserBusinessService businessService;

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable @NotNull UUID userId) {
        return businessService.getUserById(userId);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return businessService.getAllUsers();
    }

    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return businessService.createUser(userRequestDto);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@PathVariable @NotNull UUID userId,
                                      @Valid @RequestBody UserRequestDto userRequestDto)
    {
        return businessService.updateUser(userId, userRequestDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @NotNull UUID userId) {
        businessService.deleteUser(userId);
    }
}
