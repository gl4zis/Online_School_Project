package ru.spring.school.online.exception;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.spring.school.online.dto.response.ErrorResponse;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({ValidationException.class, UsernameIsTakenException.class,
            UsernameNotFoundException.class, EmailIsTakenException.class})
    public ErrorResponse invalidInputBodyHandler(Exception e) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse invalidUserDataHandler(BadCredentialsException e) {
        return new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse accessDenied(AccessDeniedException e) {
        return new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE,
                e.getMessage());
    }
}
