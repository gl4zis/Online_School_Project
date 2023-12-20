package ru.school.userservice.dto;

import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.school.userservice.validation.email.ValidEmail;
import ru.school.userservice.validation.name.ValidName;
import ru.school.userservice.validation.username.ValidUsername;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class ProfileData {
    private Long id;
    @ValidUsername
    private String username;
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
    @DateTimeFormat(pattern = "yyy-mm-dd")
    @Past
    private Date birthdate;
    private Long photoId;

    // Only teacher
    private Set<String> subjects;
    private String description;
}
