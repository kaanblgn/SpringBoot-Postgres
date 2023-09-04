package com.sekom.banking.business;

import com.sekom.banking.dto.request.UserRequestDto;
import com.sekom.banking.dto.response.UserResponseDto;
import com.sekom.banking.entity.User;
import com.sekom.banking.enums.EntityType;
import com.sekom.banking.mapper.CustomMapper;
import com.sekom.banking.service.UserService;
import com.sekom.banking.service.impl.CrudException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBusinessService {

    private final UserService userService;
    private final CustomMapper mapper;

    public UserResponseDto getUserById(UUID userId) {
       return userService.getUserById(userId)
               .map(mapper::mapToResponseDto)
               .orElseThrow(() ->  CrudException.entityNotFound(EntityType.USER, String.valueOf(userId)));
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> userList = userService.getAllUsers();

        if (userList.isEmpty()){
            throw CrudException.entitiesNotFound(EntityType.USER);
        }

        return userList.stream().map(mapper::mapToResponseDto).toList();
    }

    public UserResponseDto createUser(UserRequestDto requestDto) {

        try {
            User createdUser = userService.createUser(mapper.mapToEntity(requestDto));
            return mapper.mapToResponseDto(createdUser);
        } catch (Exception e) {
            throw CrudException.createEntityFailed(EntityType.USER, e);
        }
    }

    public UserResponseDto updateUser(UUID userId, UserRequestDto requestDto)
    {
        User userToUpdate = userService.getUserById(userId)
                .orElseThrow(() -> CrudException.entityNotFound(EntityType.USER, userId.toString()));

        try {
            User updatedUser = userService.updateUser(mapper.mapToUpdateEntity(userToUpdate, requestDto));
            return mapper.mapToResponseDto(updatedUser);
        } catch (Exception e){
            throw CrudException.updateEntityFailed(EntityType.USER, userId.toString(), e);
        }
    }

    public void deleteUser(UUID userId) {
        User userToDelete = userService.getUserById(userId)
                .orElseThrow(() -> CrudException.entityNotFound(EntityType.USER, userId.toString()));

        try {
            userService.deleteUser(userToDelete);
        } catch (Exception e) {
            throw CrudException.deleteEntityFailed(EntityType.USER, userId.toString(), e);
        }
    }


}
