package ru.school.courseservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.school.response.ErrorResponse;

@RestControllerAdvice
@ResponseBody
public class HandlerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CourseNotExists.class)
    public ErrorResponse badRequest(Exception e) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage());
    }
}
