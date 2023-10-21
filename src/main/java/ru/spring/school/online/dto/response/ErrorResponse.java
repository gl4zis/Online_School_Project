package ru.spring.school.online.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Schema(description = "Error response entity")
@Data
public class ErrorResponse {
    @Schema(description = "Time of error")
    private Date timestamp;
    @Schema(description = "HTTP response status code")
    private int status;
    @Schema(description = "Reason of the error")
    private String reason;

    public ErrorResponse(HttpStatus status, String reason) {
        this.status = status.value();
        this.reason = reason;
        this.timestamp = new Date();
    }
}
