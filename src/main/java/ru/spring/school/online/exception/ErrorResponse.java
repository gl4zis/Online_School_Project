package ru.spring.school.online.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private final String reason;
    private final Date timestamp;

    public ErrorResponse(String reason) {
        this.reason = reason;
        this.timestamp = new Date();
    }
}
