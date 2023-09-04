package com.sekom.banking.service;

import com.sekom.banking.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getUserById(UUID userId);
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
}

