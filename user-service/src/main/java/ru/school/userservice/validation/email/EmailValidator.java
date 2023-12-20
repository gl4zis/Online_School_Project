package ru.school.userservice.validation.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Value("${regex.email}")
    private String emailPattern;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || Pattern.compile(emailPattern).matcher(value).find();
    }
}
