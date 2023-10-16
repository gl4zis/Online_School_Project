package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.school.online.dto.request.AuthRequest;
import ru.spring.school.online.service.AuthService;
import ru.spring.school.online.utils.ResponseUtils;
import ru.spring.school.online.utils.ValidationUtils;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;
    private final ResponseUtils responseUtils;
    private final ValidationUtils validationUtils;

    @PostMapping
    public ResponseEntity<?> authorize(HttpServletRequest request,
                                       @RequestBody @Valid AuthRequest auth,
                                       Errors errors
    ) {
        final String path = request.getServletPath();
        if (errors.hasErrors()) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    validationUtils.errorsToString(errors),
                    path
            );
        }

        try {
            String jwToken = authService.loginUser(auth);
            return responseUtils.returnToken(jwToken);
        } catch (BadCredentialsException e) {
            return responseUtils.returnError(
                    HttpStatus.UNAUTHORIZED,
                    e.getMessage(),
                    path
            );
        }
    }
}
