package ru.school.authservice.dto.response;

import lombok.Data;

@Data
public class JwtResponse {
    private final String type = "Bearer";
    private final String access;
    private final String refresh;
}
