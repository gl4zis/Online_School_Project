package ru.school.authservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PublicAccountInfo {
    private String username;
    private String email;
    private Set<String> roles;
    private boolean locked;
}
