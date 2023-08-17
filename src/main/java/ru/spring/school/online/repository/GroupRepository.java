package ru.spring.school.online.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spring.school.online.model.security.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
