package ru.school.fileservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.school.JwtTokenUtils;
import ru.school.exception.InvalidTokenException;
import ru.school.fileservice.dto.FileRequest;
import ru.school.fileservice.exception.InvalidFileException;
import ru.school.fileservice.utils.FileManager;
import ru.school.fileservice.utils.ImageManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final JwtTokenUtils jwtTokenUtils;
    private final FileManager fileManager;
    private final ImageManager imageManager;

    public String upload(FileRequest file, HttpServletRequest request) throws InvalidFileException {
        Long owner = null;
        Optional<String> token = jwtTokenUtils.getAccessToken(request);
        if (token.isPresent() && jwtTokenUtils.validateAccess(token.get()))
            owner = jwtTokenUtils.getIdFromAccess(token.get());

        try {
            String key = fileManager.generateKey(file.name(), owner);

            if (!fileManager.isFileExists(key))
                fileManager.save(file.data(), key);

            return key;
        } catch (IOException | NullPointerException e) {
            throw new InvalidFileException();
        }
    }

    public byte[] getFile(String key) throws FileNotFoundException {
        return fileManager.byteArray(key);
    }

    public byte[] getScaledImage(String key, Integer size) throws IOException {
        return imageManager.getImg(key, size);
    }

    public void removeFile(String key, HttpServletRequest request) throws InvalidTokenException {
        Optional<String> token = jwtTokenUtils.getAccessToken(request);
        if (token.isEmpty() || !jwtTokenUtils.validateAccess(token.get()))
            throw new InvalidTokenException();

        Long userId = jwtTokenUtils.getIdFromAccess(token.get());
        if (jwtTokenUtils.accessHasRole(token.get(), "ROLE_ADMIN") ||
                key.endsWith(userId.toString())) {
            fileManager.remove(key);
        }
    }
}
