package ru.spring.school.online.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private final String username;
    private final String password;
}
