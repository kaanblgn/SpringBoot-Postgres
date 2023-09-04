package com.sekom.banking.dto.request;

import com.sekom.banking.enums.UserIdentityType;
import com.sekom.banking.validation.ValidUserIdentityType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    private String password;

    @NotBlank(message = "Phone number cannot be empty.")
    @Pattern(regexp = "^5[0-9]{9}$", message = "Invalid phone number format.")
    private String phoneNumber;

    private String address;

    @ValidUserIdentityType
    private UserIdentityType identityType;

    @NotBlank(message = "Identity number cannot be empty.")
    @Size(min = 8, max = 11, message = "Identity number must be between minimum 8 and maximum 11 digits long.")
    private String identityNo;

}
