package ru.school.fileservice.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.school.fileservice.exception.InvalidFileException;

import java.io.*;
import java.nio.file.Paths;

@Component
public class FileManager {
    @Value("${file.root-path}")
    private String ROOT;

    public boolean fileNotExists(String key) {
        return !filePath(key).exists();
    }

    private File filePath(String key) {
        return Paths.get(ROOT, key).toFile();
    }

    public void save(byte[] data, String key) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(filePath(key))) {
            stream.write(data);
        }
    }

    public byte[] getFile(String key) throws FileNotFoundException {
        try (FileInputStream stream = new FileInputStream(filePath(key))) {
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new FileNotFoundException("No such file");
        }
    }

    public String generateKey(Long owner) {
        String key = RandomStringUtils.random(16, true, true);
        return owner == null ? key : key + "_" + owner;
    }

    public void remove(String key) throws InvalidFileException {
        try {
            boolean deleted = filePath(key).delete();
            if (!deleted)
                throw new IOException();
        } catch (IOException | SecurityException e) {
            throw new InvalidFileException("Can't delete file");
        }
    }
}
