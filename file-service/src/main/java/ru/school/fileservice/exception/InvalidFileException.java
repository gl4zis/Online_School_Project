package ru.school.fileservice.exception;

public class InvalidFileException extends Exception {
    public InvalidFileException() {
        super("Invalid file request");
    }
}
