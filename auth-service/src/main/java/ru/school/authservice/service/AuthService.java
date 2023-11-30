package ru.school.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.school.authservice.dto.request.LoginRequest;
import ru.school.authservice.dto.response.JwtResponse;
import ru.school.authservice.model.Account;
import ru.school.authservice.utils.JwtTokenUtils;
import ru.school.authservice.utils.ValidationUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountService accountService;
    private final ValidationUtils validationUtils;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    // AuthExc, ValidationExc
    public JwtResponse login(LoginRequest request) {
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

    private JwtResponse setUserTokens(Account account) {
        String refresh = jwtTokenUtils.generateRefreshToken(account);
        account.setRefreshToken(refresh);
        accountService.saveAccount(account);
        String access = jwtTokenUtils.generateAccessToken(account);
        return new JwtResponse(access, refresh);
    }
}
