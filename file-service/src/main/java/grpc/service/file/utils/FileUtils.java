package grpc.service.file.utils;

import grpc.service.file.model.UserFile;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class FileUtils {
    public String getFileBase64(UserFile file) {
        return String.format("data:%s;base64,%s", file.getContentType(),
                Base64.getEncoder().encodeToString(file.getContent()));
    }
}
