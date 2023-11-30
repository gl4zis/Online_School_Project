package ru.school.fileservice.service;

import jakarta.servlet.http.HttpServletRequest;
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

    public Long saveNewFile(MultipartFile input, HttpServletRequest request) throws InvalidFileException {
        if (input.getSize() == 0)
            throw new InvalidFileException();

        String owner = null;
        Optional<String> token = jwtTokenUtils.getAccessToken(request);
        if (token.isPresent() && jwtTokenUtils.validateAccess(token.get()))
            owner = jwtTokenUtils.getUsernameFromAccess(token.get());

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

    public void removeFile(Long key, HttpServletRequest request) throws InvalidTokenException {
        Optional<String> token = jwtTokenUtils.getAccessToken(request);
        if (token.isEmpty() || !jwtTokenUtils.validateAccess(token.get()))
            throw new InvalidTokenException();

        String username = jwtTokenUtils.getUsernameFromAccess(token.get());
        Optional<UserFile> file = fileRepository.findById(key);
        if (file.isPresent() && (username.equals(file.get().getOwner()) ||
                jwtTokenUtils.accessHasRole(token.get(), "ROLE_ADMIN"))
        ) {
            fileRepository.deleteById(key);
        }
    }
}
