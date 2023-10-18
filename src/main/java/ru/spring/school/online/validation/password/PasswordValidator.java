package ru.spring.school.online.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern regex = Pattern.compile("^\\S+$");
        return value == null || (value.length() >= 6 &&
                value.length() <= 50 && regex.matcher(value).find());
    }
}
