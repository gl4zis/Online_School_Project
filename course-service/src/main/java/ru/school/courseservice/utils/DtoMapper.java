package ru.school.courseservice.utils;

import org.springframework.stereotype.Component;
import ru.school.courseservice.dto.CourseCreateData;
import ru.school.courseservice.model.Course;

@Component
public class DtoMapper {
    public Course newCourse(CourseCreateData data) {
        return Course.builder()
                .name(data.getName())
                .price(data.getPrice())
                .imageId(data.getImageId())
                .subject(data.getSubject())
                .summary(data.getSummary())
                .description(data.getDescription())
                .published(false)
                .teacherId(data.getTeacherId())
                .build();
    }
}
