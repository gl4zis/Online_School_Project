package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.exception.EmailIsTakenException;
import ru.spring.school.online.exception.UsernameIsTakenException;
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
    public ResponseEntity<?> getSelfProfile(HttpServletRequest request,
                                            Authentication auth) {
        return profileService.getProfile(request, auth.getName());
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
        return profileService.getProfile(request, username);
    }

    @PutMapping
    public ResponseEntity<?> updateWholeUser(HttpServletRequest request,
                                             Authentication auth,
                                             @RequestBody ProfileUpdateDto profileDto
    ) {
        try {
            profileService.updateWholeProfile(profileDto, auth.getName());
            return ResponseEntity.ok(new MessageResponse("Profile was updated"));
        } catch (ValidationException | UsernameIsTakenException | UsernameNotFoundException | EmailIsTakenException e) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    request.getServletPath()
            );
        }
    }
}