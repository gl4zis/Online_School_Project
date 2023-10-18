package ru.spring.school.online.validation.username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern regex = Pattern.compile("^[\\w\\d]+$");
        return value == null || (value.length() >= 3 &&
                value.length() <= 20 && regex.matcher(value).find());
    }
}
