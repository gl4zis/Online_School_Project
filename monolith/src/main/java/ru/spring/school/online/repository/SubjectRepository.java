package ru.spring.school.online.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spring.school.online.model.course.Subject;

public interface SubjectRepository extends CrudRepository<Subject, String> {
}
