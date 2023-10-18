package ru.spring.school.online.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.utils.DtoMappingUtils;
import ru.spring.school.online.utils.ResponseUtils;
import ru.spring.school.online.utils.ValidationUtils;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final Validator validator;
    private final ValidationUtils validationUtils;
    private final UserService userService;
    private final DtoMappingUtils dtoMappingUtils;
    private final ResponseUtils responseUtils;
    private final SubjectService subjectService;

    public ResponseEntity<?> getProfile(HttpServletRequest request, String username) {
        try {
            User user = (User) userService.loadUserByUsername(username);
            return ResponseEntity.ok(dtoMappingUtils.profileFromUser(user));
        } catch (UsernameNotFoundException e) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    request.getServletPath()
            );
        }
    }

    public void updateWholeProfile(ProfileUpdateDto dto, String oldUsername)
            throws UsernameNotFoundException, ValidationException, UsernameIsTakenException {
        if (!validate(dto))
            throw new ValidationException("Some required field is null");

        userService.updateProfile(oldUsername, dto);
    }

    private boolean validate(ProfileUpdateDto dto) throws UsernameNotFoundException, ValidationException {
        Set<ConstraintViolation<ProfileUpdateDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(validationUtils.errorsToString(errors));
        }

        User user = (User) userService.loadUserByUsername(dto.getUsername());
        User.Role role = user.getRole();

        if (role == User.Role.ADMIN)
            return true;
        else if (role == User.Role.STUDENT)
            return dto.getFirstname() != null && dto.getLastname() != null &&
                    dto.getBirthdate() != null && dto.getGrade() != null;
        else
            return dto.getFirstname() != null && dto.getLastname() != null &&
                    dto.getPhotoBase64() != null && dto.getSubjects() != null &&
                    subjectService.allSubjectsExists(dto.getSubjects()) &&
                    dto.getEducation() != null && dto.getDiplomasBase64() != null &&
                    dto.getWorkExperience() != null;
    }
}
