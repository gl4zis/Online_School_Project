package ru.spring.school.online.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.request.LoginUserDto;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.utils.JwtTokenUtils;
import ru.spring.school.online.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final ValidationUtils validationUtils;

    public String loginUser(LoginUserDto user) throws BadCredentialsException, ValidationException {
        validationUtils.validateAndThrowException(user);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        UserDetails userDetails = (User) auth.getPrincipal();
        return jwtTokenUtils.generateToken(userDetails);
    }

    public String registerUtil(User user) throws ValidationException, UsernameIsTakenException {
        validationUtils.validateAndThrowException(user);

        if (!userService.isUsernameUnique(user.getUsername()))
            throw new UsernameIsTakenException(user.getUsername());

        userService.saveUser(user);
        return jwtTokenUtils.generateToken(user);
    }
}
