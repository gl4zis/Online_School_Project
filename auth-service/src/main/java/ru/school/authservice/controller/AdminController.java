package ru.school.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.authservice.dto.AuthWithRoleRequest;
import ru.school.authservice.service.AuthService;

@RestController
@RequestMapping("/admin")
@ResponseBody
@RequiredArgsConstructor
public class AdminController {
    private final AuthService authService;


    // 400 (Validation, UsernameIsTaken), 403 (NotAccess)
    @PostMapping("/signup")
    public void signup(@RequestBody AuthWithRoleRequest request) {
        authService.adminSignUp(request);
    }

}
