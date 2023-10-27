package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.spring.school.online.exception.FileNotFoundException;
import ru.spring.school.online.model.UserFile;
import ru.spring.school.online.repository.FileRepository;
import ru.spring.school.online.utils.FileUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepo;
    private final FileUtils fileUtils;

    public void removeFile(UserFile file) {
        fileRepo.delete(file);
        fileUtils.removeFile(file.getKey());
    }

    public UserFile saveNewFile(MultipartFile file) throws IOException {
        String fileKey = generateKey(file.getName());
        UserFile userFile = new UserFile();
        userFile.setKey(fileKey);
        userFile.setContentType(file.getContentType());
        userFile.setSize(file.getSize());
        fileRepo.save(userFile);
        fileUtils.saveFile(file.getBytes(), fileKey);
        return userFile;
    }

    public String getFileBased64(String fileKey) throws IOException, FileNotFoundException {
        String type = fileRepo.findById(fileKey)
                .orElseThrow(() -> new FileNotFoundException(fileKey)).getContentType();
        return "data:" + type + ";base64," + Base64.getEncoder().encodeToString(fileUtils.getBytesFromFile(fileKey));
    }

    private String generateKey(String name) {
        return DigestUtils.md5Hex(name + LocalDateTime.now());
    }
}
