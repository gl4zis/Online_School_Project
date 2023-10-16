package ru.spring.school.online.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.school.online.exception.IdNotFoundException;
import ru.spring.school.online.model.security.Course;
import ru.spring.school.online.repository.CourseRepository;

import java.util.Optional;

@Service
public class CourseService {
    final CourseRepository courseRepo;

    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public Course findCourse(Long id) {
        Optional<Course> courseOpt = courseRepo.findById(id);
        if (courseOpt.isPresent()) {
            return courseOpt.get();
        }
        throw new IdNotFoundException(String.format("Course with id %d not found.", id));
    }

    public Iterable<Course> allCourses() {
        return courseRepo.findAll();
    }

    public void saveCourse(Course course) {
        courseRepo.save(course);
    }

    public boolean deleteCourse(Long id) {
        if (courseRepo.findById(id).isPresent()) {
            courseRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
