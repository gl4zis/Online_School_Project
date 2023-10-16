package ru.spring.school.online.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private final Date timestamp;
    private final int status;
    private final String reason;
    private final String path;

    public ErrorResponse(int status, String reason, String path) {
        this.status = status;
        this.reason = reason;
        this.path = path;
        this.timestamp = new Date();
    }
}
