package ru.school.userservice.dto.request;

import lombok.Data;

@Data
public class UserLockRequest {
    private long user;
    private boolean lock;
}
