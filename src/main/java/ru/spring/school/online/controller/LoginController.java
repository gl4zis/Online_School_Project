package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.request.LoginUserDto;
import ru.spring.school.online.dto.response.JwtResponse;
import ru.spring.school.online.service.AuthService;

@RestController
@Tag(name = "Controller for authorization", description = "All users should log in through it")
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    @Operation(summary = "Authorize any accounts through it",
            description = "Send username, password and take JWT token after authorization. " +
                    "You can send email instead of  username")
    @PostMapping
    @ResponseBody
    public JwtResponse authorize(@RequestBody LoginUserDto loginDto) {
        return new JwtResponse(authService.loginUser(loginDto));
    }
}
