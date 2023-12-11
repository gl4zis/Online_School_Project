package ru.school.fileservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.school.fileservice.model.UserFile;

@Repository
public interface FileRepository extends CrudRepository<UserFile, Long> {
}
