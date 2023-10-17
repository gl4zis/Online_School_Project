package ru.spring.school.online.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String reason;
    private String path;

    public ErrorResponse(int status, String reason, String path) {
        this.status = status;
        this.reason = reason;
        this.path = path;
        this.timestamp = new Date();
    }
}
