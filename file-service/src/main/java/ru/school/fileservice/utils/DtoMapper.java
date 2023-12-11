package ru.school.fileservice.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.school.fileservice.model.UserFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

@Component
public class DtoMapper {
    public UserFile fileFromMultipart(MultipartFile input, Long owner) throws IOException {
        if (input == null)
            throw new FileNotFoundException("No file in request");

        return UserFile
                .builder()
                .name(input.getOriginalFilename())
                .size(input.getSize())
                .contentType(input.getContentType())
                .content(input.getBytes())
                .ownerId(owner)
                .build();
    }

    public String base64FromFile(UserFile file) {
        return String.format("data:%s;base64,%s", file.getContentType(),
                Base64.getEncoder().encodeToString(file.getContent()));
    }
}
