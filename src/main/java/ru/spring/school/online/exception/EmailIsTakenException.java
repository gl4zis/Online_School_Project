package ru.spring.school.online.exception;

public class EmailIsTakenException extends Exception {
    public EmailIsTakenException(String email) {
        super("Email '" + email + "' is already taken");
    }
}
