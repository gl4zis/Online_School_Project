package ru.school.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.school.ValidationUtils;
import ru.school.authservice.dto.request.AuthRequest;
import ru.school.authservice.dto.response.JwtResponse;
import ru.school.authservice.exception.UsernameIsTakenException;
import ru.school.authservice.model.Account;
import ru.school.authservice.security.JwtGenerator;
import ru.school.authservice.utils.DtoMapper;
import ru.school.exception.InvalidTokenException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountService accountService;
    private final ValidationUtils validationUtils;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final DtoMapper mapper;

    public JwtResponse login(AuthRequest request) {
        validationUtils.validateOrThrowException(request);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Account account = (Account) auth.getPrincipal();
        return setUserTokens(account);
    }

    public JwtResponse signupStudent(AuthRequest request) {
        validationUtils.validateOrThrowException(request);

        if (!accountService.isUsernameUnique(request.getUsername()))
            throw new UsernameIsTakenException(request.getUsername());

        Account newStudent = mapper.createNewStudent(request);
        return setUserTokens(newStudent);
    }

    public JwtResponse updateTokens(String oldRefresh) throws InvalidTokenException {
        if (!jwtGenerator.validateRefresh(oldRefresh))
            throw new InvalidTokenException();

        Account account = accountService.getByRefresh(oldRefresh);
        return setUserTokens(account);
    }

    private JwtResponse setUserTokens(Account account) {
        String refresh = jwtGenerator.generateRefreshToken(account);
        account.setRefreshToken(refresh);
        accountService.saveAccount(account);
        String access = jwtGenerator.generateAccessToken(account);
        return new JwtResponse(access, refresh);
    }
}
