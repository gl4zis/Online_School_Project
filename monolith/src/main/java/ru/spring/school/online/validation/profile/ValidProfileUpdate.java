package ru.spring.school.online.validation.profile;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UpdateProfileValidator.class)
public @interface ValidProfileUpdate {
    String message() default "Some required fields is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
