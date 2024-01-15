package ru.school.courseservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.school.courseservice.exception.CourseNotExists;
import ru.school.courseservice.model.Course;
import ru.school.courseservice.repository.CourseRepository;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Iterable<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Iterable<Course> getTeacherCourses(Long teacherId) {
        return courseRepository.getAllByTeacherId(teacherId);
    }

    public Iterable<Course> getStudentCourses(Long studentId) {
        return courseRepository.getAllByStudentIdsContains(studentId);
    }

    public Course getById(Long id) throws CourseNotExists {
        return courseRepository.findById(id).orElseThrow(CourseNotExists::new);
    }
}
