package ru.school.fileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.school.fileservice.dto.ErrorResponse;

import java.io.FileNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHolder {

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
    @ExceptionHandler({InvalidFileException.class, IllegalArgumentException.class})
    public ErrorResponse badRequest(Exception e) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(InvalidTokenException.class)
    public ErrorResponse noAccess(Exception e) {
        return new ErrorResponse(
                HttpStatus.FORBIDDEN,
                e.getMessage());
    }
}
