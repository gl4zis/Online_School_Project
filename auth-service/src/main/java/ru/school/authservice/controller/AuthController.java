package ru.school.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.school.authservice.dto.request.AuthRequest;
import ru.school.authservice.dto.request.RefreshToken;
import ru.school.authservice.dto.response.JwtResponse;
import ru.school.authservice.service.AuthService;
import ru.school.exception.InvalidTokenException;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 400 (Validation), 401 (BadCredentials)
    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    // 400 (Validation, NotUnique)
    @PostMapping("/signup")
    public JwtResponse signup(@RequestBody AuthRequest request) {
        return authService.signupStudent(request);
    }

    // 403 (InvalidToken)
    @PostMapping("/tokens")
    public JwtResponse updateTokens(@RequestBody RefreshToken token) throws InvalidTokenException {
        return authService.updateTokens(token.getRefresh());
    }
}
