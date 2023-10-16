package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.AuthRequest;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.utils.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    public String loginUser(AuthRequest request) throws BadCredentialsException {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (User) auth.getPrincipal();
        return jwtTokenUtils.generateToken(userDetails);
    }

    public String regNewUser(User user) throws UsernameIsTakenException {
        if (!userService.isUsernameUnique(user.getUsername()))
            throw new UsernameIsTakenException("User '" + user.getUsername() + "' already exists");
        userService.saveUser(user);
        return jwtTokenUtils.generateToken(user);
    }
}
