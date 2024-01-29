package ru.school.userservice.dto.request;

import lombok.Data;

@Data
public class UserPublishRequest {
    private long user;
    private boolean published;
}
