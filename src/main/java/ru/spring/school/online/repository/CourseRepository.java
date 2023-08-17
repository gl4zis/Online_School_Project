package ru.spring.school.online.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spring.school.online.model.security.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
