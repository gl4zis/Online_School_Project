package ru.school.fileservice.exception;

public class InvalidFileException extends Exception {
    public InvalidFileException(String message) {
        super(message);
    }
    public InvalidFileException() {
        super("Invalid file request");
    }
}
