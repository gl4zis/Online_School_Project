package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.spring.school.online.validation.grade.ValidGrade;
import ru.spring.school.online.validation.name.ValidName;
import ru.spring.school.online.validation.password.ValidPassword;
import ru.spring.school.online.validation.username.ValidUsername;

import java.util.Date;

@Data
public class StudentRegDto {
    @NotNull(message = "{user.username.null}")
    @ValidUsername
    private String username;

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;

    @NotNull(message = "{user.firstname.null}")
    @ValidName(message = "{user.firstname.invalid}")
    private String firstname;

    @NotNull(message = "{user.lastname.null}")
    @ValidName(message = "{user.lastname.invalid}")
    private String lastname;

    @NotNull(message = "{user.birthdate.empty}")
    @Past(message = "{user.birthdate.in-future}")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthdate;

    @NotNull(message = "{student.grade.null}")
    @ValidGrade
    private Byte grade;
}
