package ru.spring.school.online.validation.profile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.SubjectService;
import ru.spring.school.online.service.UserService;

@Component
@RequiredArgsConstructor
public class UpdateProfileValidator implements ConstraintValidator<ValidProfileUpdate, ProfileUpdateDto> {
    private final UserService userService;
    private final SubjectService subjectService;

    @Override
    public boolean isValid(ProfileUpdateDto dto, ConstraintValidatorContext context) {
        User user = (User) userService.loadUserByUsername(dto.getUsername());
        if (user.hasRole(User.Role.STUDENT) && (dto.getFirstname() == null ||
                dto.getLastname() == null || dto.getBirthdate() == null || dto.getGrade() == null))
            return false;

        return !user.hasRole(User.Role.TEACHER) || (dto.getFirstname() != null && dto.getLastname() != null &&
                dto.getPhotoBase64() != null && dto.getSubjects() != null &&
                subjectService.allSubjectsExists(dto.getSubjects()) &&
                dto.getEducation() != null && dto.getDiplomasBase64() != null &&
                dto.getWorkExperience() != null);
    }
}
