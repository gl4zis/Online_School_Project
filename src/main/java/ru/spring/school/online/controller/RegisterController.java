package ru.spring.school.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.JwtResponse;
import ru.spring.school.online.dto.StudentRegister;
import ru.spring.school.online.exception.ErrorResponse;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.AuthService;
import ru.spring.school.online.service.UserService;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegister register) {
        Student user = userService.getStudentFromRegister(register);
        try {
            return ResponseEntity.ok(new JwtResponse(authService.regNewUser(user)));
        } catch (UsernameIsTakenException e) {
            return new ResponseEntity<>(
                    new ErrorResponse(e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}