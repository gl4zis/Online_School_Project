package ru.spring.school.online.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Getter
public class StorageService {
    @Value("${storage.img.dir}")
    private String imgStorageDir;

    public void saveFile(MultipartFile file, String uploadDir, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public boolean isFileValid(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    public String getNormalizedFileName(MultipartFile file) throws IOException {
        if (file.getOriginalFilename() != null) {
            return StringUtils.cleanPath(file.getOriginalFilename());
        }
        throw new IOException("Null file name");
    }

}
