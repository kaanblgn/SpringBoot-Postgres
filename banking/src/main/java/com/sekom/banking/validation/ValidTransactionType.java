package com.sekom.banking.validation;

import jakarta.validation.Constraint;

@Constraint(validatedBy = TransactionTypeValidator.class)
public @interface ValidTransactionType {
    String message() default "Invalid transaction type";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
