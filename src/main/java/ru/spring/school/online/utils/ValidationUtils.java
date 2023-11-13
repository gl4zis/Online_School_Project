package ru.spring.school.online.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationUtils {
    private final Validator validator;

    public void validateOrThrowException(Object o) throws ValidationException {
        Set<ConstraintViolation<Object>> errors = validator.validate(o);
        if (!errors.isEmpty())
            throw new ValidationException(errorsToString(errors));
    }

    public String errorsToString(Set<ConstraintViolation<Object>> errors) {
        return String.join("; ", errors.stream()
                .map(ConstraintViolation::getMessage).toList());
    }
}
