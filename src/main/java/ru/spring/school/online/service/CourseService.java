package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.school.online.repository.CourseRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepo;

    public Set<String> getStudentCourseNames(String studentUserName) {
        return courseRepo.findAllByStudent(studentUserName);
    }

    public Set<String> getTeacherCourseNames(String teacherCourseName) {
        return courseRepo.findAllByTeacher(teacherCourseName);
    }
}
