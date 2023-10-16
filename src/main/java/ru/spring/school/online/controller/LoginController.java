package ru.spring.school.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.AuthRequest;
import ru.spring.school.online.dto.JwtResponse;
import ru.spring.school.online.exception.ErrorResponse;
import ru.spring.school.online.service.AuthService;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> authorize(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(new JwtResponse(authService.loginUser(request)));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    new ErrorResponse(e.getMessage()),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}
