package ru.school.fileservice.exception;

import java.io.IOException;

public class InvalidFileException extends IOException {
    public InvalidFileException() {
        super("Invalid file in request");
    }
}
