package ru.spring.school.online.exception;

public class UsernameIsTakenException extends Exception {
    public UsernameIsTakenException(String message) {
        super(message);
    }
}
