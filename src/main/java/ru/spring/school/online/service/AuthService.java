package ru.spring.school.online.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ru.spring.school.online.dto.request.LoginUserDto;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.utils.JwtTokenUtils;
import ru.spring.school.online.utils.ResponseUtils;
import ru.spring.school.online.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final ResponseUtils responseUtils;
    private final ValidationUtils validationUtils;

    public String loginUser(LoginUserDto user) throws BadCredentialsException {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        UserDetails userDetails = (User) auth.getPrincipal();
        return jwtTokenUtils.generateToken(userDetails);
    }

    public String regNewUser(User user) throws UsernameIsTakenException {
        if (!userService.isUsernameUnique(user.getUsername()))
            throw new UsernameIsTakenException(user.getUsername());
        userService.saveUser(user);
        return jwtTokenUtils.generateToken(user);
    }

    public ResponseEntity<?> registerUtil(HttpServletRequest request, User user, Errors errors) {
        final String path = request.getServletPath();
        if (errors.hasErrors()) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    validationUtils.errorsToString(errors),
                    path);
        }

        try {
            String jwToken = regNewUser(user);
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
