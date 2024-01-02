package ru.school.fileservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import ru.school.JwtTokenUtils;
import ru.school.exception.InvalidTokenException;
import ru.school.fileservice.exception.InvalidFileException;
import ru.school.fileservice.utils.FileManager;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final JwtTokenUtils jwtTokenUtils;
    private final FileManager fileManager;

    public String upload(byte[] data, HttpServletRequest request) throws InvalidFileException {
        Long owner = null;
        Optional<String> token = jwtTokenUtils.getAccessToken(request);
        if (token.isPresent() && jwtTokenUtils.validateAccess(token.get()))
            owner = jwtTokenUtils.getIdFromAccess(token.get());

        try {
            String key = fileManager.generateKey(data, owner);

            if (fileManager.fileNotExists(key))
                fileManager.save(data, key);

            return key;
        } catch (IOException | NullPointerException e) {
            throw new InvalidFileException();
        }
    }

    public byte[] getFile(String key, Integer size) throws IOException {
        if (size == null)
            return fileManager.getFile(key);
        else if (size > 0 && size <= 4000)
            return fileManager.getImage(key, size);
        else
            throw new BadRequestException("Invalid image size");
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
