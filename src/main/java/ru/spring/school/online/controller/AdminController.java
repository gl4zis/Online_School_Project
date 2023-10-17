package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.school.online.dto.request.AuthRequest;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    // Using class AuthRequest just because it is exactly the same with AdminRegistry / UnconfirmedTeacherRegistry
    @PostMapping("/register/admin")
    public ResponseEntity<?> regAdmin(HttpServletRequest request,
                                      @RequestBody @Valid AuthRequest register,
                                      Errors errors
    ) {
        User user = register.toAdmin(passwordEncoder);
        return authService.registerUtil(request, user, errors);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<?> regTeacher(HttpServletRequest request,
                                        @RequestBody @Valid AuthRequest register,
                                        Errors errors
    ) {
        User user = register.toTeacher(passwordEncoder);
        return authService.registerUtil(request, user, errors);
    }
}
