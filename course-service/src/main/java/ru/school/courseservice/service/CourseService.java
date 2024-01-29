package ru.school.courseservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.school.JwtTokenUtils;
import ru.school.courseservice.dto.CourseCreateData;
import ru.school.courseservice.exception.CourseNotExists;
import ru.school.courseservice.model.Course;
import ru.school.courseservice.repository.CourseRepository;
import ru.school.courseservice.utils.DtoMapper;
import ru.school.exception.InvalidTokenException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final DtoMapper dtoMapper;

    private boolean isValidAdmin(HttpServletRequest request) {
        Optional<String> tokenOpt = jwtTokenUtils.getAccessToken(request);
        return tokenOpt.isPresent() &&
                jwtTokenUtils.validateAccess(tokenOpt.get()) &&
                jwtTokenUtils.accessHasRole(tokenOpt.get(), "ROLE_ADMIN");
    }

    public Long createCourse(HttpServletRequest request, CourseCreateData data) throws InvalidTokenException {
        if (!isValidAdmin(request))
            throw new InvalidTokenException();

        Course course = courseRepository.save(dtoMapper.newCourse(data));
        return course.getId();
    }

    public Iterable<Course> getAllCourses(HttpServletRequest request) throws InvalidTokenException {
        if (!isValidAdmin(request))
            throw new InvalidTokenException();

        return courseRepository.findAll();
    }

    public void setPublished(Long id, boolean published, HttpServletRequest request)
            throws CourseNotExists, InvalidTokenException
    {
        if (!isValidAdmin(request))
            throw new InvalidTokenException();

        Course course = courseRepository.findById(id).orElseThrow(CourseNotExists::new);
        course.setPublished(published);
        courseRepository.save(course);
    }

    public void removeCourse(Long id, HttpServletRequest request) throws InvalidTokenException {
        if (!isValidAdmin(request))
            throw new InvalidTokenException();

        courseRepository.deleteById(id);
    }

    public Iterable<Course> getPublishedCourses() {
        return courseRepository.getAllByPublished(true);
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

    public Boolean isCourseNameUnique(String name) {
        return !courseRepository.existsByName(name);
    }
}
