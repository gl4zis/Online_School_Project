package ru.school.online.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.school.online.dto.ErrorResponse;

import javax.naming.ServiceUnavailableException;
import java.io.FileNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(FileNotFoundException.class)
    public ErrorResponse resourceNotFound(Exception e) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse badRequest(Exception e) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    @ExceptionHandler(ServiceUnavailableException.class)
    public ErrorResponse noConnection(Exception e) {
        return new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                e.getMessage());
    }
}
