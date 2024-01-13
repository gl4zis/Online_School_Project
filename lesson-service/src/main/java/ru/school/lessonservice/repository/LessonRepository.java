package ru.school.lessonservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.school.lessonservice.model.Lesson;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
