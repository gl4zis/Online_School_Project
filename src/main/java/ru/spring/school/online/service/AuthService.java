package ru.spring.school.online.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.request.AdminOrTeacherRegDto;
import ru.spring.school.online.dto.request.LoginUserDto;
import ru.spring.school.online.dto.request.StudentRegDto;
import ru.spring.school.online.dto.response.JwtResponse;
import ru.spring.school.online.exception.AuthException;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.utils.DtoMappingUtils;
import ru.spring.school.online.utils.JwtTokenUtils;
import ru.spring.school.online.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final ValidationUtils validationUtils;
    private final DtoMappingUtils dtoMappingUtils;

    public JwtResponse loginUser(LoginUserDto userDto) throws BadCredentialsException, ValidationException {
        validationUtils.validateOrThrowException(userDto);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();
        return setUserTokens(user);
    }

    public JwtResponse updateTokens(String oldRefresh) throws AuthException {
        User user = userService.getByToken(oldRefresh);
        return setUserTokens(user);
    }

    private JwtResponse setUserTokens(User user) {
        String refresh = jwtTokenUtils.generateRefreshToken(user);
        user.setRefreshToken(refresh);
        userService.saveUser(user);
        String access = jwtTokenUtils.generateAccessToken(user);
        return new JwtResponse(access, refresh);
    }

    public void registerUser(AdminOrTeacherRegDto userDto) throws ValidationException, UsernameIsTakenException {
        validationUtils.validateOrThrowException(userDto);
        User user = dtoMappingUtils.newUser(userDto);
        registerUtil(user);
    }

    public JwtResponse registerStudent(StudentRegDto studentDto) throws ValidationException, UsernameIsTakenException {
        validationUtils.validateOrThrowException(studentDto);
        User user = dtoMappingUtils.newStudent(studentDto);
        return registerUtil(user);
    }

    private JwtResponse registerUtil(User user) throws ValidationException, UsernameIsTakenException {
        if (!userService.isUsernameUnique(user.getUsername()))
            throw new UsernameIsTakenException(user.getUsername());

        return setUserTokens(user);
    }
}
