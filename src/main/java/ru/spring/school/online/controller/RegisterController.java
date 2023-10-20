package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.school.online.dto.request.StudentRegDto;
import ru.spring.school.online.dto.response.JwtResponse;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.AuthService;
import ru.spring.school.online.utils.DtoMappingUtils;

@RestController
@Tag(name = "Controller for registration",
        description = "Only students can registration accounts themselves through this controller. " +
                "Other roles should be registered by admins")
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final AuthService authService;
    private final DtoMappingUtils dtoMappingUtils;

    @PostMapping
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegDto studentDto) {
        User user = dtoMappingUtils.newStudent(studentDto);
        return ResponseEntity.ok(new JwtResponse(authService.registerUtil(user)));
    }
}