package ru.school.userservice.validation.username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Value("${regex.username}")
    private String usernamePattern;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern regex = Pattern.compile(usernamePattern);
        return value == null || regex.matcher(value).find();
    }
}
