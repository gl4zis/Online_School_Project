package ru.school.userservice.exception;

public class EmailIsTakenException extends RuntimeException {
    public EmailIsTakenException(String email) {
        super("User with email " + email + " already exists");
    }
}
