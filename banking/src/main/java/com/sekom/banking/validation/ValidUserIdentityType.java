package com.sekom.banking.validation;

import jakarta.validation.Constraint;

@Constraint(validatedBy = UserIdentityTypeValidator.class)
public @interface ValidUserIdentityType {
    String message() default "Invalid identity type";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
