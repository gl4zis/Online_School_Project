package ru.spring.school.online.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.spring.school.online.model.course.Course;

import java.util.Set;

public interface CourseRepository extends CrudRepository<Course, String> {
    @Query("SELECT c.name FROM Course c JOIN c.groups g JOIN g.teacher t WHERE t.username = :teacher")
    Set<String> findAllByTeacher(@Param("teacher") String username);

    @Query("SELECT c.name FROM Course c JOIN c.groups g JOIN g.students s WHERE s.username = :student")
    Set<String> findAllByStudent(@Param("student") String username);
}
