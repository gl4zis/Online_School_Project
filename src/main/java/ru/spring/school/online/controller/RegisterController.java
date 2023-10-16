package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.school.online.dto.request.StudentRegister;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.service.AuthService;
import ru.spring.school.online.utils.ResponseUtils;
import ru.spring.school.online.utils.ValidationUtils;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final ResponseUtils responseUtils;
    private final ValidationUtils validationUtils;

    @PostMapping
    public ResponseEntity<?> registerStudent(HttpServletRequest request,
                                             @RequestBody @Valid StudentRegister register,
                                             Errors errors
    ) {
        final String path = request.getServletPath();
        if (errors.hasErrors()) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    validationUtils.errorsToString(errors),
                    path);
        }

        Student user = register.toStudent(passwordEncoder);
        try {
            String jwToken = authService.regNewUser(user);
            return responseUtils.returnToken(jwToken);
        } catch (UsernameIsTakenException e) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    path
            );
        }
    }
}