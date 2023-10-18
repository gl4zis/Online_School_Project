package ru.spring.school.online.utils;

import jakarta.validation.ConstraintViolation;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ru.spring.school.online.dto.request.ProfileUpdateDto;

import java.util.Set;

@Service
public class ValidationUtils {

    public String errorsToString(Errors errors) {
        return String.join("; ", errors.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
    }

    public String errorsToString(Set<ConstraintViolation<ProfileUpdateDto>> errors) {
        return String.join("; ", errors.stream()
                .map(ConstraintViolation::getMessage).toList());
    }
}
