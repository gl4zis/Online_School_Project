package ru.spring.school.online.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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

    public ErrorResponse(int status, String reason) {
        this.status = status;
        this.reason = reason;
        this.timestamp = new Date();
    }
}
