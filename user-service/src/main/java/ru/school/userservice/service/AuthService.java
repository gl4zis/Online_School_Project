package ru.school.userservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.school.JwtTokenUtils;
import ru.school.ValidationUtils;
import ru.school.exception.InvalidTokenException;
import ru.school.userservice.dto.request.AuthRequest;
import ru.school.userservice.dto.request.PasswordsDto;
import ru.school.userservice.dto.request.RegWithRoleRequest;
import ru.school.userservice.dto.request.RegRequest;
import ru.school.userservice.dto.response.JwtResponse;
import ru.school.userservice.exception.InvalidPasswordException;
import ru.school.userservice.exception.UsernameIsTakenException;
import ru.school.userservice.model.User;
import ru.school.userservice.security.JwtGenerator;
import ru.school.userservice.utils.DtoMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final ValidationUtils validationUtils;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final JwtTokenUtils jwtTokenUtils;
    private final DtoMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(AuthRequest request) {
        validationUtils.validateOrThrowException(request);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();
        return setUserTokens(user);
    }

    public JwtResponse signupStudent(RegRequest request) {
        validationUtils.validateOrThrowException(request);

        if (!userService.isUsernameUnique(request.getUsername()))
            throw new UsernameIsTakenException(request.getUsername());

        User newStudent = mapper.createNewUser(request, User.Role.STUDENT);
        userService.saveUser(newStudent);
        return setUserTokens(newStudent);
    }

    public JwtResponse updateTokens(String oldRefresh) throws InvalidTokenException {
        if (!jwtGenerator.validateRefresh(oldRefresh))
            throw new InvalidTokenException();

        User user = userService.getByRefresh(oldRefresh);
        return setUserTokens(user);
    }

    public void adminSignUp(RegWithRoleRequest request) {
        validationUtils.validateOrThrowException(request);

        if (!userService.isUsernameUnique(request.getUsername()))
            throw new UsernameIsTakenException(request.getUsername());

        userService.saveUser(mapper.createNewUser(request, request.getRole()));
    }

    private JwtResponse setUserTokens(User user) {
        String access = jwtGenerator.generateAccessToken(user);
        String refresh = jwtGenerator.generateRefreshToken(user);
        user.setRefreshToken(refresh);

        userService.saveUser(user);
        return new JwtResponse(access, refresh, jwtTokenUtils.getAccessExpiredTime(access));
    }

    public void removeUser(HttpServletRequest req) throws InvalidTokenException {
        Optional<String> token = jwtTokenUtils.getAccessToken(req);
        if (token.isEmpty() || !jwtTokenUtils.validateAccess(token.get()))
            throw new InvalidTokenException();

        userService.removeUser(jwtTokenUtils.getIdFromAccess(token.get()));
    }

    public void changePassword(HttpServletRequest req, PasswordsDto passwords)
            throws InvalidTokenException, InvalidPasswordException {
        Optional<String> token = jwtTokenUtils.getAccessToken(req);
        if (token.isEmpty() || !jwtTokenUtils.validateAccess(token.get()))
            throw new InvalidTokenException();

        User user = userService.getById(jwtTokenUtils.getIdFromAccess(token.get()));
        if (!passwordEncoder.matches(passwords.getOldPassword(), user.getPassword()))
            throw new InvalidPasswordException();

        user.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
        userService.saveUser(user);
    }
}
