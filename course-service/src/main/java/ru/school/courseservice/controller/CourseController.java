package ru.school.courseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.school.courseservice.model.Course;
import ru.school.courseservice.repository.CourseRepository;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "Course controller")
public class CourseController {
    private final CourseRepository courseRepository;

    @Operation(summary = "Returns list of all courses")
    @GetMapping("/all")
    public Iterable<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
