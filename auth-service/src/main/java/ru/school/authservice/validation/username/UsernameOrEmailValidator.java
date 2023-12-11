package ru.school.authservice.validation.username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UsernameOrEmailValidator implements ConstraintValidator<ValidUsernameOrEmail, String> {
    @Value("${regex.username}")
    private String usernamePattern;

    @Value("${regex.email}")
    private String emailPattern;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern usernameRegex = Pattern.compile(usernamePattern);
        Pattern emailRegex = Pattern.compile(emailPattern);
        return value == null || usernameRegex.matcher(value).find() || emailRegex.matcher(value).find();
    }
}
