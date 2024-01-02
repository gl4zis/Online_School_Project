package ru.school.fileservice.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {
    @Value("${file.root-path}")
    private String ROOT;

    public boolean isFileExists(String key) {
        Path path = Paths.get(ROOT, key);
        return path.toFile().isFile();
    }

    public void save(byte[] data, String key) throws IOException {
        Path path = Paths.get(ROOT, key);
        Path file = Files.createFile(path);

        try (FileOutputStream stream = new FileOutputStream(file.toString())) {
            stream.write(data);
        }
    }

    public byte[] byteArray(String key) throws FileNotFoundException {
        Path path = Paths.get(ROOT, key);
        try (FileInputStream stream = new FileInputStream(path.toFile())) {
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new FileNotFoundException("No such file");
        }
    }

    public String generateKey(String filename, Long owner) {
        return owner == null ? DigestUtils.md5Hex(filename) :
                DigestUtils.md5Hex(filename) + owner;
    }

    public void remove(String key) {
        try {
            Path path = Paths.get(ROOT, key);
            Files.delete(path);
        } catch (IOException ignored) {
        }
    }
}
