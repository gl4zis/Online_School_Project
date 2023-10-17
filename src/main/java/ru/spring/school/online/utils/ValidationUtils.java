package ru.spring.school.online.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class ValidationUtils {

    public String errorsToString(Errors errors) {
        return String.join("; ", errors.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
    }
}
