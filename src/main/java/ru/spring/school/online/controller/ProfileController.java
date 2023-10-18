package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.service.ProfileService;
import ru.spring.school.online.service.UserService;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

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

/*    @PutMapping
    public ResponseEntity<?> updateWholeUser(HttpServletRequest request,
                                             @RequestBody @Valid ProfileUpdateDto profileDto,
                                             Errors errors
    ) {
        if (errors.hasErrors()) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    validationUtils.errorsToString(errors),
                    request.getServletPath()
            );
        }

        return ResponseEntity.ok(new MessageResponse("Functionality in development"));
    }*/
}