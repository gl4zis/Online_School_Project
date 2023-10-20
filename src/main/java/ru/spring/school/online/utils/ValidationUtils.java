package ru.spring.school.online.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.SubjectService;
import ru.spring.school.online.service.UserService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationUtils {
    private final Validator validator;
    private final UserService userService;
    private final SubjectService subjectService;

    public void validateAndThrowException(Object o) throws ValidationException {
        Set<ConstraintViolation<Object>> errors = validator.validate(o);
        if (!errors.isEmpty())
            throw new ValidationException(errorsToString(errors));
    }

    public void validateProfileUpdateAndThrowException(ProfileUpdateDto dto) throws ValidationException {
        validateAndThrowException(dto);

        User user = (User) userService.loadUserByUsername(dto.getUsername());

        String exceptionMessage = "Some of required value is null";

        if (user.hasRole(User.Role.STUDENT) && (dto.getFirstname() == null ||
                dto.getLastname() == null || dto.getBirthdate() == null || dto.getGrade() == null))
            throw new ValidationException(exceptionMessage);
        if (user.hasRole(User.Role.TEACHER) && (dto.getFirstname() == null || dto.getLastname() == null ||
                dto.getPhotoBase64() == null || dto.getSubjects() == null ||
                subjectService.allSubjectsExists(dto.getSubjects()) ||
                dto.getEducation() == null || dto.getDiplomasBase64() == null ||
                dto.getWorkExperience() == null))
            throw new ValidationException(exceptionMessage);

    }

    public String errorsToString(Set<ConstraintViolation<Object>> errors) {
        return String.join("; ", errors.stream()
                .map(ConstraintViolation::getMessage).toList());
    }
}
