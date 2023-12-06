package ru.school.profileservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.school.JwtTokenUtils;
import ru.school.exception.InvalidTokenException;
import ru.school.profileservice.exception.ProfileNotFoundException;
import ru.school.profileservice.model.Profile;
import ru.school.profileservice.repository.ProfileRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public void updateProfile(HttpServletRequest req, Profile profile) throws InvalidTokenException {
        Optional<String> access = jwtTokenUtils.getAccessToken(req);
        if (access.isEmpty() || !jwtTokenUtils.validateAccess(access.get()))
            throw new InvalidTokenException();

        Long accountId = jwtTokenUtils.getIdFromAccess(access.get());
        profile.setAccountId(accountId);
        profileRepository.save(profile);
    }

    public Profile getSelfProfile(HttpServletRequest req) throws InvalidTokenException, ProfileNotFoundException {
        Optional<String> access = jwtTokenUtils.getAccessToken(req);
        if (access.isEmpty() || !jwtTokenUtils.validateAccess(access.get()))
            throw new InvalidTokenException();

        Long accountId = jwtTokenUtils.getIdFromAccess(access.get());
        return getProfile(accountId);
    }

    public Profile getProfile(Long accountId) throws ProfileNotFoundException {
        Optional<Profile> profile = profileRepository.findById(accountId);
        if (profile.isEmpty())
            throw new ProfileNotFoundException();

        return profile.get();
    }
}
