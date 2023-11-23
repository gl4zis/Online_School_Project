package ru.spring.school.online.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spring.school.online.model.UserFile;

public interface FileRepository extends CrudRepository<UserFile, String> {
}
