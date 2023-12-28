package ru.school.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.exception.InvalidTokenException;
import ru.school.response.MessageResponse;
import ru.school.userservice.dto.request.AuthRequest;
import ru.school.userservice.dto.request.PasswordsDto;
import ru.school.userservice.dto.request.RegRequest;
import ru.school.userservice.dto.response.JwtResponse;
import ru.school.userservice.dto.response.RefreshToken;
import ru.school.userservice.exception.InvalidPasswordException;
import ru.school.userservice.service.AuthService;
import ru.school.userservice.service.UserService;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "Authorization controller")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Removes account of authorized user", description = "Throws 403 (InvalidToken")
    @DeleteMapping
    public void removeSelfAccount(HttpServletRequest req) throws InvalidTokenException {
        authService.removeUser(req);
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
    public JwtResponse signup(@RequestBody RegRequest request) {
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
        return new MessageResponse(Boolean.toString(userService.isUsernameUnique(username)));
    }

    @Operation(summary = "Endpoint for changing account password", description = "Throws 400 " +
            "(Invalid old password), 403 (Invalid token)")
    @PostMapping("/passwords")
    public void changePassword(@Valid @RequestBody PasswordsDto passwords, HttpServletRequest req)
            throws InvalidTokenException, InvalidPasswordException {
        authService.changePassword(req, passwords);
    }
}
