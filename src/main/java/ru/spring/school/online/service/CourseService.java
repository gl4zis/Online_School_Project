package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.school.online.repository.CourseRepository;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepo;

    public Iterable<String> getStudentCourseNames(String studentUserName) {
        return courseRepo.findAllByStudent(studentUserName);
    }

    public Iterable<String> getTeacherCourseNames(String teacherCourseName) {
        return courseRepo.findAllByTeacher(teacherCourseName);
    }
}
