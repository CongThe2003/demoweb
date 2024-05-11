package com.thecode.demoweb.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

//    @Override
//    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        WebUser form = (WebUser) value;
//        return form.getPassword() != null && form.getPassword().equals(form.getRepeatPassword());
//    }
}

