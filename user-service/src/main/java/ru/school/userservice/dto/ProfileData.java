package ru.school.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import ru.school.userservice.validation.email.ValidEmail;
import ru.school.userservice.validation.name.ValidName;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class ProfileData {
    private Long id;
    @ValidEmail
    private String email;
    private String role;
    private boolean locked;

    @ValidName
    private String firstname;
    @ValidName
    private String lastname;
    @ValidName
    private String middleName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Moscow")
    @Past
    private Date birthdate;
    private String photoId;
    private Boolean published;

    // Only teacher
    private Set<String> subjects;
    private String description;
}
