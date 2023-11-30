package ru.school.authservice.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.school.response.ErrorResponse;

@RestControllerAdvice
public class HandlerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({UsernameIsTakenException.class, ValidationException.class})
    public ErrorResponse badRequest(Exception e) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse badCredentials(Exception e) {
        return new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                e.getMessage());
    }
}
