package ru.spring.school.online.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.spring.school.online.dto.transfer.Profile;

import java.util.Set;

@Data
@JsonView(Profile.class)
public class TeacherDto {
    private Set<String> subjects;

    private Set<String> diplomasBase64;

    private String education;

    private String description;

    private Byte workExperience;
}
