package com.sekom.banking.validation;

import com.sekom.banking.enums.UserIdentityType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

class UserIdentityTypeValidator implements ConstraintValidator<ValidUserIdentityType, UserIdentityType> {
    @Override
    public boolean isValid(UserIdentityType value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(UserIdentityType.values()).contains(value);
    }
}