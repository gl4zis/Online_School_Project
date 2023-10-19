package ru.spring.school.online.validation.name;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern regex = Pattern.compile("^[\\s\\wа-яА-Я,.\\-']{2,50}$");
        return value == null || regex.matcher(value).find();
    }
}
