package ru.school.fileservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.school.JwtTokenUtils;
import ru.school.exception.InvalidTokenException;
import ru.school.fileservice.exception.InvalidFileException;
import ru.school.fileservice.utils.FileManager;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final JwtTokenUtils jwtTokenUtils;
    private final FileManager fileManager;

    public String upload(String base64, HttpServletRequest request) throws InvalidFileException {
        Long owner = null;
        Optional<String> token = jwtTokenUtils.getAccessToken(request);
        if (token.isPresent() && jwtTokenUtils.validateAccess(token.get()))
            owner = jwtTokenUtils.getIdFromAccess(token.get());

        String content = base64.substring(base64.indexOf(',') + 1);

        byte[] data = Base64.getMimeDecoder().decode(content);
        String key = fileManager.generateKey(owner);

        try {
            if (fileManager.fileNotExists(key))
                fileManager.save(data, key);

            return key;
        } catch (IOException | NullPointerException e) {
            throw new InvalidFileException();
        }
    }

    public void removeFile(String key, HttpServletRequest request)
            throws InvalidTokenException, InvalidFileException
    {
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
