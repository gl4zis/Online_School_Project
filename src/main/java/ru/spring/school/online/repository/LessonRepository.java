package ru.spring.school.online.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spring.school.online.model.course.Lesson;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
