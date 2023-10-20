package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.service.ProfileService;
import ru.spring.school.online.service.UserService;

@RestController
@Tag(name = "Controller for interaction with your and other's profile")
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getSelfProfile(Authentication auth) {
        return profileService.getProfile(auth.getName());
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteProfile(Authentication auth) {
        userService.deleteUser(auth.getName());
        return ResponseEntity.ok(new MessageResponse("Profile was deleted"));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getOtherProfile(@PathVariable("username") String username) {
        return profileService.getProfile(username);
    }

    @PutMapping
    public ResponseEntity<?> updateWholeUser(Authentication auth,
                                             @RequestBody ProfileUpdateDto profileDto) {
        profileService.updateWholeProfile(profileDto, auth.getName());
        return ResponseEntity.ok(new MessageResponse("Profile was updated"));
    }
}