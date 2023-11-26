package ru.school.fileservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.school.fileservice.exception.InvalidFileException;
import ru.school.fileservice.model.UserFile;
import ru.school.fileservice.repository.FileRepository;
import ru.school.fileservice.utils.DtoMapper;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final DtoMapper mapper;

    public Long saveNewFile(MultipartFile input) throws InvalidFileException {
        try {
            UserFile file = mapper.fileFromMultipart(input);
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

    public void removeFile(Long key) {
        fileRepository.deleteById(key);
    }
}
