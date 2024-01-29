package ru.school.courseservice.dto;

import lombok.Data;

@Data
public class CourseCreateData {
    private String name;
    private String imageId;
    private Long price;
    private String subject;
    private String summary;
    private String description;
    private Long teacherId;
}
