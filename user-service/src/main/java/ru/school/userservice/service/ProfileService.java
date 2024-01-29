package ru.school.userservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.school.JwtTokenUtils;
import ru.school.exception.InvalidTokenException;
import ru.school.userservice.dto.ProfileData;
import ru.school.userservice.model.User;
import ru.school.userservice.utils.DtoMapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final DtoMapper dtoMapper;

    public void updateProfile(HttpServletRequest req, ProfileData profile) throws InvalidTokenException {
        Optional<String> access = jwtTokenUtils.getAccessToken(req);
        if (access.isEmpty() || !jwtTokenUtils.validateAccess(access.get()))
            throw new InvalidTokenException();

        Long accountId = jwtTokenUtils.getIdFromAccess(access.get());
        User oldUser = userService.getById(accountId);

        userService.saveUser(dtoMapper.updateUser(oldUser, profile));
    }

    public ProfileData getSelfProfile(HttpServletRequest req) throws InvalidTokenException {
        Optional<String> access = jwtTokenUtils.getAccessToken(req);
        if (access.isEmpty() || !jwtTokenUtils.validateAccess(access.get()))
            throw new InvalidTokenException();

        Long accountId = jwtTokenUtils.getIdFromAccess(access.get());
        return getProfile(accountId);
    }

    public ProfileData getProfile(Long accountId) {
        User user = userService.getById(accountId);
        return dtoMapper.getUserProfileData(user);
    }

    public Iterable<ProfileData> getAllProfiles() {
        Iterable<User> users = userService.getAll();
        Set<ProfileData> profiles = new HashSet<>();
        for (User user : users)
            profiles.add(dtoMapper.getUserProfileData(user));

        return profiles;
    }

    public Iterable<ProfileData> publishedProfilesByRole(User.Role role) {
        Iterable<User> users = userService.getByRole(role);
        Set<ProfileData> profiles = new HashSet<>();
        for (User user : users) {
            if (user.getPublished())
                profiles.add(dtoMapper.getUserProfileData(user));
        }

        return profiles;
    }
}
