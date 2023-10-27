package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.dto.response.ProfileInfo;
import ru.spring.school.online.model.UserFile;
import ru.spring.school.online.service.FileService;
import ru.spring.school.online.service.ProfileService;
import ru.spring.school.online.service.UserService;

import java.io.IOException;

@RestController
@Tag(name = "Controller for interaction with your and other's profile")
@RequestMapping("/profile")
@RequiredArgsConstructor
@ResponseBody
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;
    private final FileService fileService;

    @Operation(summary = "Returns profile of authorized user", description = "Gives all non-security user info")
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ProfileInfo getSelfProfile(Authentication auth) {
        return profileService.getProfile(auth.getName());
    }

    @Operation(summary = "Deletes account of authorized user")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping
    public MessageResponse deleteProfile(Authentication auth) {
        userService.deleteUser(auth.getName());
        return new MessageResponse("Profile was deleted");
    }

    @Operation(summary = "Returns profile of given user", description = "Gives all non-security user info")
    @GetMapping("/{username}")
    public ProfileInfo getOtherProfile(@PathVariable String username) {
        return profileService.getProfile(username);
    }

    @Operation(summary = "Updates authorized user account",
            description = "Requests every necessary (for user role) field to be not null")
    @SecurityRequirement(name = "JWT")
    @PutMapping
    public MessageResponse updateWholeUser(Authentication auth,
                                           @RequestBody ProfileUpdateDto profileDto) {
        profileService.updateWholeProfile(profileDto, auth.getName());
        return new MessageResponse("Profile was updated");
    }

    @PatchMapping("/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public UserFile setUserPhoto(Authentication auth,
                                 @RequestParam MultipartFile photo) throws IOException {
        UserFile userFile = fileService.saveNewFile(photo);
        userService.updatePhoto(auth.getName(), userFile);
        return userFile;
    }
}