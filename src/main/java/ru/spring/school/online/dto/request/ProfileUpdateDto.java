package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.spring.school.online.validation.experience.ValidExperience;
import ru.spring.school.online.validation.grade.ValidGrade;
import ru.spring.school.online.validation.name.ValidName;
import ru.spring.school.online.validation.password.ValidPassword;
import ru.spring.school.online.validation.profile.ValidProfileUpdate;
import ru.spring.school.online.validation.username.ValidUsername;

import java.util.Date;
import java.util.Set;

@Data
@ValidProfileUpdate
public class ProfileUpdateDto {
    @NotNull(message = "{user.username.null}")
    @ValidUsername
    private String username; // *

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password; // *

    @Email(message = "{user.email.invalid}")
    private String email;

    @ValidName
    private String firstname; //Student|Teacher *
    @ValidName
    private String lastname; //Student|Teacher *
    @ValidName
    private String middleName;
    @Past(message = "{user.birthdate.in-future}")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthdate; //Student *
    private String photoBase64; //Teacher *

    @ValidGrade
    private Byte grade; //Student-only *

    @Size(message = "{teacher.subjects.wrong-size}", min = 1, max = 3)
    private Set<String> subjects; // Teacher-only *
    private String education; // Teacher-only *
    private Set<String> diplomasBase64; // Teacher-only *
    private String description; //Teacher-only
    @ValidExperience
    private Byte workExperience; // Teacher-only *
}
