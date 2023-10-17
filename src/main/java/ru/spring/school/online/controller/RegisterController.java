package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.school.online.dto.UserDto;
import ru.spring.school.online.dto.transfer.StudentReg;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.AuthService;
import ru.spring.school.online.utils.DtoMappingUtils;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final AuthService authService;
    private final DtoMappingUtils dtoMappingUtils;

    @PostMapping
    public ResponseEntity<?> registerStudent(HttpServletRequest request,
                                             @RequestBody @Validated(StudentReg.class) UserDto userDto,
                                             Errors errors
    ) {
        User user = dtoMappingUtils.userDtoToStudent(userDto);
        return authService.registerUtil(request, user, errors);
    }
}