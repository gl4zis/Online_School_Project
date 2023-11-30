package ru.school.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.authservice.dto.AuthRequest;
import ru.school.authservice.dto.JwtResponse;
import ru.school.authservice.dto.RefreshToken;
import ru.school.authservice.service.AccountService;
import ru.school.authservice.service.AuthService;
import ru.school.exception.InvalidTokenException;
import ru.school.response.MessageResponse;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AccountService accountService;

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

    @GetMapping("/unique/{username}")
    public MessageResponse isUsernameUnique(@PathVariable("username") String username) {
        return new MessageResponse(Boolean.toString(accountService.isUsernameUnique(username)));
    }
}
