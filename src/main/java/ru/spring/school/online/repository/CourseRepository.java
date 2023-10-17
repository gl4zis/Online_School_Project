package ru.spring.school.online.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.spring.school.online.model.course.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

    //TODO: Check it

    @Query("SELECT c.name FROM Course c JOIN c.groups g JOIN g.teacher t WHERE t.username = :teacher")
    Iterable<String> findAllByTeacher(@Param("teacher") String username);

    @Query("SELECT c.name FROM Course c JOIN c.groups g JOIN g.students s WHERE s.username = :student")
    Iterable<Course> findAllByStudent(@Param("student") String username);
}
