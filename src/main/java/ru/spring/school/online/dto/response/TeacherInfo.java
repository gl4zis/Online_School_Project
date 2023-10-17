package ru.spring.school.online.dto.response;

import lombok.Data;
import ru.spring.school.online.model.course.Subject;

import java.util.Set;

@Data
public class TeacherInfo {
    private Set<Subject> subjects;
    private String education;

    private Set<String> diplomasBase64;

    private String description;
    private Byte workExperience;

    //TODO: Realise
    // private Set<Course> courses;
}
