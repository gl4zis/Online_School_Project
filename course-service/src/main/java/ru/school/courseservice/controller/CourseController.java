package ru.school.courseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
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

    @Operation(summary = "Returns all courses, that this teacher teach or on that this student registered")
    @GetMapping("/{userId}")
    public Iterable<Course> getAllUserCourses(@PathVariable Long userId, @RequestParam String role) {
        if (role.equals("TEACHER"))
            return courseService.getTeacherCourses(userId);
        else if (role.equals("STUDENT"))
            return courseService.getStudentCourses(userId);
        else
            throw new ValidationException("Invalid param");
    }
}
