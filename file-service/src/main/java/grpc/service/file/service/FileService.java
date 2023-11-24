package grpc.service.file.service;

import grpc.service.file.Void;
import grpc.service.file.*;
import grpc.service.file.model.UserFile;
import grpc.service.file.repository.FileRepository;
import grpc.service.file.utils.FileUtils;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
public class FileService extends FileServiceGrpc.FileServiceImplBase {
    private final FileRepository fileRepository;
    private final FileUtils fileUtils;

    @Override
    public void getFileBase64(FileKey key, StreamObserver<FileBase64> responseObserver) {
        Optional<UserFile> fileOptional = fileRepository.findById(key.getKey());

        if (fileOptional.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            return;
        }

        FileBase64 response = FileBase64
                .newBuilder()
                .setFile(fileUtils.getFileBase64(fileOptional.get()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveNewFile(FileInfo file, StreamObserver<FileKey> responseObserver) {
        UserFile entity = UserFile
                .builder()
                .name(file.getName())
                .contentType(file.getType())
                .size(file.getSize())
                .content(file.getContent().toByteArray())
                .build();

        entity = fileRepository.save(entity);
        FileKey response = FileKey.newBuilder().setKey(entity.getKey()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void removeFile(FileKey key, StreamObserver<Void> resp) {
        fileRepository.deleteById(key.getKey());
        resp.onNext(Void.newBuilder().build());
        resp.onCompleted();
    }
}
