package ru.spring.school.online.validation.grade;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GradeValidator implements ConstraintValidator<ValidGrade, Byte> {
    @Override
    public boolean isValid(Byte value, ConstraintValidatorContext context) {
        return value != null && value >= 1 && value <= 11;
    }
}
