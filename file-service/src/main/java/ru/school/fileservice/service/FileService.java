package ru.school.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.school.JwtTokenUtils;
import ru.school.exception.InvalidTokenException;
import ru.school.fileservice.exception.InvalidFileException;
import ru.school.fileservice.model.UserFile;
import ru.school.fileservice.repository.FileRepository;
import ru.school.fileservice.utils.DtoMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final JwtTokenUtils jwtTokenUtils;
    private final FileRepository fileRepository;
    private final DtoMapper mapper;

    public Long saveNewFile(MultipartFile input, String token) throws InvalidFileException {
        String owner = null;
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (jwtTokenUtils.validateAccess(token))
                owner = jwtTokenUtils.getUsernameFromAccess(token);
        }

        try {
            UserFile file = mapper.fileFromMultipart(input, owner);
            file = fileRepository.save(file);
            return file.getId();
        } catch (IOException e) {
            throw new InvalidFileException();
        }
    }

    public String getFileBase64(Long key) throws FileNotFoundException {
        UserFile file = fileRepository.findById(key)
                .orElseThrow(() -> new FileNotFoundException("No such file in db"));
        return mapper.base64FromFile(file);
    }

    public void removeFile(Long key, String token) throws InvalidTokenException {
        if (token == null || !token.startsWith("Bearer ") ||
                !jwtTokenUtils.validateAccess(token.substring(7)))
            throw new InvalidTokenException();

        Optional<UserFile> fileOpt = fileRepository.findById(key);
        if (fileOpt.isEmpty())
            return;

        String username = jwtTokenUtils.getUsernameFromAccess(token.substring(7));

        if (jwtTokenUtils.accessHasRole(token.substring(7), "ROLE_ADMIN") ||
                username.equals(fileOpt.get().getOwner()))
            fileRepository.deleteById(key);
    }
}
