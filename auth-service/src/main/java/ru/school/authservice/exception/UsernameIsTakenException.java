package ru.school.authservice.exception;

public class UsernameIsTakenException extends RuntimeException {
    public UsernameIsTakenException(String username) {
        super("User '" + username + "' is already exists");
    }
}
