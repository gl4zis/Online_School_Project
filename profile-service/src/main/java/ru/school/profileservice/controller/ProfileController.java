package ru.school.profileservice.controller;

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
public class ProfileController {
    private final ProfileService profileService;

    // 400 (NotValid json), 403 (Invalid token)
    @PutMapping
    public void updateProfile(HttpServletRequest req,
                              @Valid @RequestBody Profile profile
    ) throws InvalidTokenException {
        profileService.updateProfile(req, profile);
    }

    // 403 (InvalidToken), 404 (No profile)
    @GetMapping
    public Profile getSelfProfile(HttpServletRequest req)
            throws InvalidTokenException, ProfileNotFoundException {
        return profileService.getSelfProfile(req);
    }

    // 400 (Invalid id), 404 (No profile)
    @GetMapping("/{id}")
    public Profile getOtherProfile(@PathVariable("id") Long id) throws ProfileNotFoundException {
        return profileService.getProfile(id);
    }
}
