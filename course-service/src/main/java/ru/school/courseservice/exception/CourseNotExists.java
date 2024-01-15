package ru.school.courseservice.exception;

public class CourseNotExists extends Exception {
    public CourseNotExists() {
        super("This course doesn't exist");
    }
}
