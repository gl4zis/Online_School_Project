package ru.school.userservice.dto.response;

public record JwtResponse(String access, String refresh, Long expiredAt) {
}
