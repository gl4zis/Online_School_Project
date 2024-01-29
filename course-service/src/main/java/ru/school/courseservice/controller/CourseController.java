package ru.school.courseservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.school.courseservice.dto.CourseCreateData;
import ru.school.courseservice.dto.CoursePublishedDto;
import ru.school.courseservice.exception.CourseNotExists;
import ru.school.courseservice.model.Course;
import ru.school.courseservice.service.CourseService;
import ru.school.exception.InvalidTokenException;
import ru.school.response.MessageResponse;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "Course controller")
public class CourseController {
    private final CourseService courseService;

    @Operation(summary = "Creates new course", description = "Admin access. No access (403), Invalid data (400)")
    @PostMapping()
    public MessageResponse createCourse(HttpServletRequest request, @RequestBody CourseCreateData data) throws InvalidTokenException {
        System.out.println(data);
        return new MessageResponse(courseService.createCourse(request, data).toString());
    }

    @Operation(summary = "Returns list of all courses")
    @GetMapping("/all")
    public Iterable<Course> getAllCourses(HttpServletRequest request) throws InvalidTokenException {
        return courseService.getAllCourses(request);
    }

    @Operation(summary = "Change course publish")
    @PutMapping
    public void setPublish(@RequestBody CoursePublishedDto req,
                           HttpServletRequest request)
            throws CourseNotExists, InvalidTokenException
    {
        courseService.setPublished(req.getId(), req.isPublished(), request);
    }

    @Operation(summary = "Remove course")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id, HttpServletRequest request) throws InvalidTokenException {
        courseService.removeCourse(id, request);
    }

    @Operation(summary = "Returns list of published courses")
    @GetMapping("/published")
    public Iterable<Course> getPublishedCourses() {
        return courseService.getPublishedCourses();
    }

    @Operation(summary = "Returns all courses, that this teacher teach or on that this student registered")
    @GetMapping("/by-user/{userId}")
    public Iterable<Course> getAllUserCourses(@PathVariable Long userId, @RequestParam String role) {
        if (role.equals("TEACHER"))
            return courseService.getTeacherCourses(userId);
        else if (role.equals("STUDENT"))
            return courseService.getStudentCourses(userId);
        else
            throw new ValidationException("Invalid param");
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) throws CourseNotExists {
        return courseService.getById(id);
    }

    @GetMapping("/name-unique/{name}")
    public MessageResponse isCourseNameUnique(@PathVariable String name) {
        return new MessageResponse(courseService.isCourseNameUnique(name).toString());
    }
}
