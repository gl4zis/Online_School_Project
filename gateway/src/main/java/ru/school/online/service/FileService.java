package ru.school.online.service;

import com.google.protobuf.ByteString;
import grpc.service.file.FileBase64;
import grpc.service.file.FileInfo;
import grpc.service.file.FileKey;
import grpc.service.file.FileServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @GrpcClient("file")
    FileServiceGrpc.FileServiceBlockingStub stub;

    public String getFileBase64(Long key) {
        FileBase64 file = stub.getFileBase64(FileKey.newBuilder().setKey(key).build());
        return file.getFile();
    }

    // TODO process exceptions
    public Long saveNewFile(MultipartFile file) throws IOException {
        FileInfo request = FileInfo
                .newBuilder()
                .setName(file.getOriginalFilename())
                .setType(file.getContentType())
                .setSize(file.getSize())
                .setContent(ByteString.copyFrom(file.getBytes()))
                .build();

        FileKey response = stub.saveNewFile(request);
        return response.getKey();
    }

    public void removeFile(Long key) {
        stub.removeFile(FileKey.newBuilder().setKey(key).build());
    }
}
