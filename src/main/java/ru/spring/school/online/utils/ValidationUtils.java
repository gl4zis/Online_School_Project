package ru.spring.school.online.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ValidationUtils {

    public String errorsToString(Errors errors) {
        return String.join("; ", errors.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
    }
}
