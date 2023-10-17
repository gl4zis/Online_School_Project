package ru.spring.school.online.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.spring.school.online.model.security.User;

import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<ValidRole, User.Role> {

    private User.Role[] subset;

    @Override
    public void initialize(ValidRole constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(User.Role value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
