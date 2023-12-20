package ru.school.userservice.exception;

public class UsernameIsTakenException extends RuntimeException {
    public UsernameIsTakenException(String username) {
        super("User '" + username + "' is already exists");
    }
}
