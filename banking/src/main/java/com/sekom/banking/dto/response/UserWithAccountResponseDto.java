package com.sekom.banking.dto.response;

import com.sekom.banking.enums.UserIdentityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithAccountResponseDto {

    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private UserIdentityType identityType;
    private String identityNo;
    private List<AccountForUserResponseDto> accounts;

}

