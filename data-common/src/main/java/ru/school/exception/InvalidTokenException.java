package ru.school.exception;

public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
        super("No access! Invalid token");
    }
}
