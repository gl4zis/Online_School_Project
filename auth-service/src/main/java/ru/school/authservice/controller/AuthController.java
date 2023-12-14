package ru.school.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.authservice.dto.AuthRequest;
import ru.school.authservice.dto.JwtResponse;
import ru.school.authservice.dto.PublicAccountInfo;
import ru.school.authservice.dto.RefreshToken;
import ru.school.authservice.service.AccountService;
import ru.school.authservice.service.AuthService;
import ru.school.exception.InvalidTokenException;
import ru.school.response.MessageResponse;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "Authorization controller")
public class AuthController {
    private final AuthService authService;
    private final AccountService accountService;

    @Operation(summary = "Removes account of authorized user", description = "Throws 403 (InvalidToken")
    @DeleteMapping
    public void removeSelfAccount(HttpServletRequest req) throws InvalidTokenException {
        authService.removeAccount(req);
    }

    @Operation(summary = "Returns unsecure account info of authorized user", description = "Throws " +
            "403 (InvalidToken), 404 (AccountNotFound)")
    @GetMapping
    public PublicAccountInfo getSelfInfo(HttpServletRequest req) throws InvalidTokenException {
        return authService.getAccountInfo(req);
    }

    @Operation(summary = "Sign in endpoint (for everybody)", description = "Requests login and password, " +
            "returns token pair. Throws 400 (Validation), 401 (BadCredentials)")
    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Sign up endpoint for students", description = "Requests only login and password, " +
            "returns token pair. Throws 400 (Validation, UsernameIsTaken)")
    @PostMapping("/signup")
    public JwtResponse signup(@RequestBody AuthRequest request) {
        return authService.signupStudent(request);
    }

    @Operation(summary = "Tokens updater", description = "Requests refresh token, returns new token pair. " +
            "Throws 403 (InvalidToken)")
    @PostMapping("/tokens")
    public JwtResponse updateTokens(@RequestBody RefreshToken token) throws InvalidTokenException {
        return authService.updateTokens(token.getRefresh());
    }

    @Operation(summary = "Check username uniqueness", description = "Throws 400 (Invalid username)")
    @GetMapping("/unique/{username}")
    public MessageResponse isUsernameUnique(@PathVariable("username") String username) {
        return new MessageResponse(Boolean.toString(accountService.isUsernameUnique(username)));
    }
}
