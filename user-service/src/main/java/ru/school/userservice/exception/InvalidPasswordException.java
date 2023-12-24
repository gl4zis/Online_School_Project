package ru.school.userservice.exception;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("Passwords doesn't matches");
    }
}
