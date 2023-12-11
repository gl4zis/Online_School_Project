package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.request.LoginUserDto;
import ru.spring.school.online.dto.request.RefreshToken;
import ru.spring.school.online.dto.request.StudentRegDto;
import ru.spring.school.online.dto.response.JwtResponse;
import ru.spring.school.online.exception.AuthException;
import ru.spring.school.online.service.AuthService;

@RestController
@Tag(name = "Controller for authorization",
        description = "Only students can registration accounts themselves through this controller. " +
                "Other roles should be registered by admins")
@RequiredArgsConstructor
@ResponseBody
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Authorize any accounts through it",
            description = "Send username, password and take JWT token after authorization. " +
                    "You can send email instead of  username")
    @PostMapping("/login")
    public JwtResponse authorize(@RequestBody LoginUserDto loginDto) {
        return authService.loginUser(loginDto);
    }

    @Operation(summary = "Regitration only for students")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse registerStudent(@RequestBody StudentRegDto studentDto) {
        return authService.registerStudent(studentDto);
    }

    @Operation(summary = "Update token pair. Need to send refresh token in body")
    @PostMapping("/tokens")
    public JwtResponse updateTokens(@RequestBody RefreshToken token) throws AuthException {
        return authService.updateTokens(token.getRefresh());
    }
}
