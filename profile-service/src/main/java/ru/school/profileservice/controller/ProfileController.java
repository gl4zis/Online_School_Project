package ru.school.profileservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.exception.InvalidTokenException;
import ru.school.profileservice.exception.ProfileNotFoundException;
import ru.school.profileservice.model.Profile;
import ru.school.profileservice.service.ProfileService;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "Profile controller")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "Updates OR CREATES account profile", description = "Access only for authorized. " +
            "Throws 400 (Validation), 403 (InvalidToken)")
    @PutMapping
    public void updateProfile(HttpServletRequest req,
                              @Valid @RequestBody Profile profile
    ) throws InvalidTokenException {
        profileService.updateProfile(req, profile);
    }

    @Operation(summary = "Returns profile of authorized user", description = "Throws 403 (InvalidToken), 404 (NoProfile)")
    @GetMapping
    public Profile getSelfProfile(HttpServletRequest req)
            throws InvalidTokenException, ProfileNotFoundException {
        return profileService.getSelfProfile(req);
    }

    @Operation(summary = "Returns another user's profile", description = "Access for everybody by account id (long). " +
            "No security info. Throws 400 (InvalidId), 404 (NoProfile)")
    @GetMapping("/{id}")
    public Profile getOtherProfile(@PathVariable("id") Long id) throws ProfileNotFoundException {
        return profileService.getProfile(id);
    }
}
