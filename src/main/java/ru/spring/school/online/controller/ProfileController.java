package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.dto.response.ProfileResponse;
import ru.spring.school.online.service.ProfileService;
import ru.spring.school.online.service.UserService;
import ru.spring.school.online.utils.ResponseUtils;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;
    private final ResponseUtils responseUtils;

    @GetMapping
    public ResponseEntity<ProfileResponse> getSelfProfile(Authentication auth) {
        return ResponseEntity.ok(profileService.getProfile(auth.getName()));
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteProfile(Authentication auth) {
        userService.deleteUser(auth.getName());
        return ResponseEntity.ok(new MessageResponse("Profile was deleted"));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getOtherProfile(HttpServletRequest request,
                                             @PathVariable("username") String username
    ) {
        try {
            return ResponseEntity.ok(profileService.getProfile(username));
        } catch (UsernameNotFoundException e) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    request.getServletPath()
            );
        }
    }
}