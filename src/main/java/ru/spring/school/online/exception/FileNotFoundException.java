package ru.spring.school.online.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String filename) {
        super("File '" + filename + "' not found");
    }
}
