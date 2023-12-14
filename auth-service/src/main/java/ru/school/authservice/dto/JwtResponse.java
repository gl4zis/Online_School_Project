package ru.school.authservice.dto;

public record JwtResponse(String access, String refresh, Long expiredAt) {
}
