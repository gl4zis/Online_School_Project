package ru.school.fileservice.utils;

import org.springframework.stereotype.Component;
import ru.school.fileservice.dto.FileRequest;
import ru.school.fileservice.model.UserFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class DtoMapper {
    public UserFile fileFromRequest(FileRequest input, Long owner) throws IOException {
        if (input == null)
            throw new FileNotFoundException("No file in request");

        return UserFile
                .builder()
                .name(input.getName())
                .size(input.getSize())
                .contentType(input.getContentType())
                .base64(input.getBase64())
                .ownerId(owner)
                .build();
    }
}
