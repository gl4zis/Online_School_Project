package ru.spring.school.online.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.spring.school.online.dto.response.ErrorResponse;
import ru.spring.school.online.dto.response.JwtResponse;

@Component
public class ResponseUtils {

    public ResponseEntity<ErrorResponse> returnError(HttpStatus status, String message, String path) {
        return new ResponseEntity<>(new ErrorResponse(
                status.value(),
                message,
                path
        ), status);
    }

    public ResponseEntity<JwtResponse> returnToken(String token) {
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
