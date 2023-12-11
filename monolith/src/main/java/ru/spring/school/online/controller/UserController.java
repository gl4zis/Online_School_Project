package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.dto.response.ProfileInfo;
import ru.spring.school.online.service.ProfileService;
import ru.spring.school.online.service.UserService;

@Tag(name = "Controller for operations with another users")
@RestController
@ResponseBody
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ProfileService profileService;
    private final UserService userService;

    @Operation(summary = "Returns profile of given user", description = "Gives all non-security user info")
    @GetMapping("/{username}/profile")
    public ProfileInfo getOtherProfile(@PathVariable String username) {
        return profileService.getProfile(username);
    }

    @Operation(summary = "Returns 'true' if there are no such username")
    @GetMapping("/unique")
    public MessageResponse isUsernameUnique(@RequestParam String username) {
        return new MessageResponse(Boolean.toString(userService.isUsernameUnique(username)));
    }
}