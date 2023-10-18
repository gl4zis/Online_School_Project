package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.school.online.dto.request.AdminOrTeacherRegDto;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.AuthService;
import ru.spring.school.online.utils.DtoMappingUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final DtoMappingUtils dtoMappingUtils;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> regAdmin(HttpServletRequest request,
                                      @RequestBody @Valid AdminOrTeacherRegDto regDto,
                                      Errors errors
    ) {
        User user = dtoMappingUtils.user(regDto);
        return authService.registerUtil(request, user, errors);
    }
}
