package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.response.ProfileInfo;
import ru.spring.school.online.service.ProfileService;

@RestController
@ResponseBody
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ProfileService profileService;

    @Operation(summary = "Returns profile of given user", description = "Gives all non-security user info")
    @GetMapping("/{username}")
    public ProfileInfo getOtherProfile(@PathVariable String username) {
        return profileService.getProfile(username);
    }
}
