package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.response.ProfileInfo;
import ru.spring.school.online.service.ProfileService;

@Tag(name = "Controller for operations with another users")
@RestController
@ResponseBody
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ProfileService profileService;

    @Operation(summary = "Returns profile of given user", description = "Gives all non-security user info")
    @GetMapping("/{username}/profile")
    public ProfileInfo getOtherProfile(@PathVariable String username) {
        return profileService.getProfile(username);
    }
}