package ru.school.courseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.courseservice.model.Course;
import ru.school.courseservice.service.CourseService;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "Course controller")
public class CourseController {
    private final CourseService courseService;

    @Operation(summary = "Returns list of all courses")
    @GetMapping("/all")
    public Iterable<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @Operation(summary = "Returns all courses, that this teacher teach")
    @GetMapping("/{teacher}")
    public Iterable<Course> getAllTeacherCourses(@PathVariable Long teacher) {
        return courseService.getTeacherCourses(teacher);
    }
}
