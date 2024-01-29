package ru.school.userservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.school.userservice.dto.ProfileData;
import ru.school.userservice.dto.request.RegRequest;
import ru.school.userservice.model.User;

@Component
@RequiredArgsConstructor
public class DtoMapper {
    private final PasswordEncoder encoder;

    public User createNewUser(RegRequest request, User.Role role) {
        return User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .locked(false)
                .role(role)
                .published(role.equals(User.Role.STUDENT) ? null : false)
                .build();
    }

    public User updateUser(User oldUser, ProfileData updateData) {
        return User.builder()
                .id(oldUser.getId())
                .password(oldUser.getPassword())
                .role(oldUser.getRole())
                .locked(oldUser.isLocked())
                .refreshToken(oldUser.getRefreshToken())
                .published(oldUser.getPublished())
                .email(updateData.getEmail())
                .firstname(updateData.getFirstname())
                .lastname(updateData.getLastname())
                .middleName(updateData.getMiddleName())
                .birthdate(updateData.getBirthdate())
                .photoId(updateData.getPhotoId())
                .subjects(updateData.getSubjects())
                .description(updateData.getDescription())
                .build();
    }

    public ProfileData getUserProfileData(User user) {
        return ProfileData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .locked(user.isLocked())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .middleName(user.getMiddleName())
                .birthdate(user.getBirthdate())
                .photoId(user.getPhotoId())
                .subjects(user.getSubjects())
                .description(user.getDescription())
                .published(user.getPublished())
                .build();
    }
}
