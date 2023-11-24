package ru.school.online.service;

import com.google.protobuf.ByteString;
import grpc.service.file.FileBase64;
import grpc.service.file.FileInfo;
import grpc.service.file.FileKey;
import grpc.service.file.FileServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.ServiceUnavailableException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class FileService {

    @GrpcClient("file")
    FileServiceGrpc.FileServiceBlockingStub stub;

    public String getFileBase64(String keyStr)
            throws ServiceUnavailableException, FileNotFoundException, BadRequestException
    {
        try {
            FileBase64 file = stub.getFileBase64(FileKey.newBuilder().setKey(Long.parseLong(keyStr)).build());
            return file.getFile();
        } catch (StatusRuntimeException e) {
            Status status = e.getStatus();
            if (status.getCode() == Status.Code.NOT_FOUND)
                throw new FileNotFoundException("File with such id is not exist");
            else
                throw new ServiceUnavailableException("No connection");
        } catch (NumberFormatException e) {
            throw new BadRequestException("Incorrect request format");
        }
    }

    public Long saveNewFile(MultipartFile file) throws BadRequestException, ServiceUnavailableException {
        try {
            FileInfo request = FileInfo
                    .newBuilder()
                    .setName(file.getOriginalFilename())
                    .setType(file.getContentType())
                    .setSize(file.getSize())
                    .setContent(ByteString.copyFrom(file.getBytes()))
                    .build();

            FileKey response = stub.saveNewFile(request);
            return response.getKey();
        } catch (IOException | NullPointerException e) {
            throw new BadRequestException("No file in request");
        } catch (StatusRuntimeException e) {
            throw new ServiceUnavailableException("No connection");
        }
    }

    public void removeFile(String keyStr)
            throws ServiceUnavailableException, BadRequestException {
        try {
            stub.removeFile(FileKey.newBuilder().setKey(Long.parseLong(keyStr)).build());
        } catch (StatusRuntimeException e) {
            throw new ServiceUnavailableException("No connection");
        } catch (NumberFormatException e) {
            throw new BadRequestException("Incorrect request format");
        }
    }
}
