package ru.school.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.exception.InvalidTokenException;
import ru.school.userservice.dto.ProfileData;
import ru.school.userservice.model.User;
import ru.school.userservice.service.ProfileService;

@RestController
@RequestMapping("/profile")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "Profile controller")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "Updates OR CREATES account profile", description = "Access only for authorized. " +
            "Throws 400 (Validation), 403 (InvalidToken)")
    @PutMapping
    public void updateProfile(HttpServletRequest req,
                              @Valid @RequestBody ProfileData profile
    ) throws InvalidTokenException {
        profileService.updateProfile(req, profile);
    }

    @Operation(summary = "Returns profile of authorized user", description = "Throws 403 (InvalidToken), 404 (NoProfile)")
    @GetMapping
    public ProfileData getSelfProfile(HttpServletRequest req)
            throws InvalidTokenException {
        return profileService.getSelfProfile(req);
    }

    @Operation(summary = "Returns another user's profile", description = "Access for everybody by account id (long). " +
            "No security info. Throws 400 (InvalidId), 404 (NoProfile)")
    @GetMapping("/{id}")
    public ProfileData getOtherProfile(@PathVariable("id") Long id) {
        return profileService.getProfile(id);
    }

    @Operation(summary = "Returns all teacher's profiles in public access")
    @GetMapping("/teachers")
    public Iterable<ProfileData> publishedTeachers() {
        return profileService.publishedProfilesByRole(User.Role.TEACHER);
    }

    @Operation(summary = "Returns all admin's profiles in public access")
    @GetMapping("/admins")
    public Iterable<ProfileData> publishedAdmins() {
        return profileService.publishedProfilesByRole(User.Role.ADMIN);
    }
}
