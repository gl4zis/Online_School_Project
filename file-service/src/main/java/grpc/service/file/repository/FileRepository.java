package grpc.service.file.repository;

import grpc.service.file.model.UserFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<UserFile, Long> {
}
