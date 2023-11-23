package ru.spring.school.online.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.spring.school.online.dto.response.ErrorResponse;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler({UsernameNotFoundException.class, FileNotFoundException.class})
    public ErrorResponse resourceNotFound(Exception e) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({ValidationException.class, UsernameIsTakenException.class,
            EmailIsTakenException.class, IOException.class})
    public ErrorResponse invalidInputBodyHandler(Exception e) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse invalidUserDataHandler(Exception e) {
        return new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler({AccessDeniedException.class, AuthException.class})
    public ErrorResponse forbidden(Exception e) {
        return new ErrorResponse(
                HttpStatus.FORBIDDEN,
                e.getMessage());
    }
}
