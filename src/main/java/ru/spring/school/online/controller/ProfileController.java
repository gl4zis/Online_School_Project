package ru.spring.school.online.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.dto.transfer.Profile;
import ru.spring.school.online.service.ProfileService;
import ru.spring.school.online.service.UserService;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @JsonView(Profile.class)
    @GetMapping
    public ResponseEntity<?> getSelfProfile(HttpServletRequest request,
                                            Authentication auth) {
        return profileService.getProfile(request, auth.getName());
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteProfile(Authentication auth) {
        userService.deleteUser(auth.getName());
        return ResponseEntity.ok(new MessageResponse("Profile was deleted"));
    }

    @JsonView(Profile.class)
    @GetMapping("/{username}")
    public ResponseEntity<?> getOtherProfile(HttpServletRequest request,
                                             @PathVariable("username") String username
    ) {
        return profileService.getProfile(request, username);
    }
}