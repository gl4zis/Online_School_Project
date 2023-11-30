package ru.school.authservice.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String reason;

    public ErrorResponse(HttpStatus status, String reason) {
        this.status = status.value();
        this.reason = reason;
        this.timestamp = new Date();
    }
}
