package ru.school.profileservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.exception.InvalidTokenException;
import ru.school.profileservice.model.Profile;
import ru.school.profileservice.service.ProfileService;

@RestController
@ResponseBody
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PutMapping
    public void updateProfile(HttpServletRequest req,
                              @Valid @RequestBody Profile profile
    ) throws InvalidTokenException {
        profileService.updateProfile(req, profile);
    }

    @GetMapping
    public Profile getSelfProfile(HttpServletRequest req) throws InvalidTokenException {
        return profileService.getSelfProfile(req);
    }

    @GetMapping("/{id}")
    public Profile getOtherProfile(@PathVariable("id") Long id) {
        return profileService.getProfile(id);
    }
}
