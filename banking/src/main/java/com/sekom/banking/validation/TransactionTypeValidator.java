package com.sekom.banking.validation;

import com.sekom.banking.enums.TransactionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

class TransactionTypeValidator implements ConstraintValidator<ValidTransactionType, TransactionType> {
    @Override
    public boolean isValid(TransactionType value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(TransactionType.values()).contains(value);
    }
}