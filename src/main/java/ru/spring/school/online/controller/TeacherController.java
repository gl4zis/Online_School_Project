package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.model.UserFile;
import ru.spring.school.online.service.FileService;
import ru.spring.school.online.service.UserService;

import java.io.IOException;

@Tag(name = "Teachers tools controller")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/teacher")
@ResponseBody
@RequiredArgsConstructor
public class TeacherController {
    private final FileService fileService;
    private final UserService userService;

    @Operation(summary = "Sets other diploma file to authorized teacher")
    @PatchMapping("/profile/diploma")
    @ResponseStatus(HttpStatus.CREATED)
    public UserFile setDiploma(Authentication auth,
                               @RequestParam MultipartFile file) throws IOException {
        UserFile userFile = fileService.saveNewFile(file);
        userService.updateDiploma(auth.getName(), userFile);
        return userFile;
    }

    @Operation(summary = "Deletes diploma file from authorized teacher account")
    @DeleteMapping("/profile//diploma")
    public MessageResponse deleteDiploma(Authentication auth) {
        userService.updateDiploma(auth.getName(), null);
        return new MessageResponse("Diploma was deleted");
    }
}
