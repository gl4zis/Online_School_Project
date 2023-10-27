package ru.spring.school.online.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TeacherProfileInfo extends ProfileInfo {
    private Set<String> subjects;
    private String education;
    private String diplomaKey;
    private String description;
    private Byte workExperience;
    protected Boolean confirmed;
}
