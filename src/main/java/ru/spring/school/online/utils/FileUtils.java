package ru.spring.school.online.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtils {
    @Value("${storage.user-files.url}")
    private String storage_url;

    public void saveFile(byte[] resource, String keyName) throws IOException {
        Path path = Paths.get(storage_url, keyName);
        Path file = Files.createFile(path);
        try (FileOutputStream stream = new FileOutputStream(file.toString())) {
            stream.write(resource);
        }
    }

    public void removeFile(String keyName) {
        Paths.get(storage_url, keyName).toFile().delete();
    }

    public byte[] getBytesFromFile(String keyName) throws IOException {
        File path = Paths.get(storage_url, keyName).toFile();
        try (FileInputStream stream = new FileInputStream(path)) {
            byte[] bytes = new byte[(int) path.length()];
            stream.read(bytes);
            return bytes;
        }
    }
}
