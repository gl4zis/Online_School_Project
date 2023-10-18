package ru.spring.school.online.validation.experience;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExperienceValidator implements ConstraintValidator<ValidExperience, Byte> {
    @Override
    public boolean isValid(Byte value, ConstraintValidatorContext context) {
        return value == null || (value >= 0 && value <= 60);
    }
}
