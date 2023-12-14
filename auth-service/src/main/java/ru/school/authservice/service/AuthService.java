package ru.school.authservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.school.JwtTokenUtils;
import ru.school.ValidationUtils;
import ru.school.authservice.dto.AuthRequest;
import ru.school.authservice.dto.AuthWithRoleRequest;
import ru.school.authservice.dto.JwtResponse;
import ru.school.authservice.dto.PublicAccountInfo;
import ru.school.authservice.exception.UsernameIsTakenException;
import ru.school.authservice.model.Account;
import ru.school.authservice.security.JwtGenerator;
import ru.school.authservice.utils.DtoMapper;
import ru.school.exception.InvalidTokenException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountService accountService;
    private final ValidationUtils validationUtils;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final JwtTokenUtils jwtTokenUtils;
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

    public void adminSignUp(AuthWithRoleRequest request) {
        validationUtils.validateOrThrowException(request);

        if (!accountService.isUsernameUnique(request.getUsername()))
            throw new UsernameIsTakenException(request.getUsername());

        accountService.saveAccount(mapper.createNewAccount(request));
    }

    private JwtResponse setUserTokens(Account account) {
        String access = jwtGenerator.generateAccessToken(account);
        String refresh = jwtGenerator.generateRefreshToken(account);
        account.setRefreshToken(refresh);

        accountService.saveAccount(account);
        return new JwtResponse(access, refresh, jwtTokenUtils.getAccessExpiredTime(access));
    }

    public void removeAccount(HttpServletRequest req) throws InvalidTokenException {
        Optional<String> token = jwtTokenUtils.getAccessToken(req);
        if (token.isEmpty() || !jwtTokenUtils.validateAccess(token.get()))
            throw new InvalidTokenException();

        accountService.removeAccount(jwtTokenUtils.getIdFromAccess(token.get()));
    }

    public PublicAccountInfo getAccountInfo(HttpServletRequest req) throws InvalidTokenException {
        Optional<String> token = jwtTokenUtils.getAccessToken(req);
        if (token.isEmpty() || !jwtTokenUtils.validateAccess(token.get()))
            throw new InvalidTokenException();

        Account account = accountService.getById(jwtTokenUtils.getIdFromAccess(token.get()));
        return mapper.getAccountInfo(account);
    }
}
