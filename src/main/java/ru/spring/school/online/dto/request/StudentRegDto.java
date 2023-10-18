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
    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @ValidName(message = "{user.firstname.invalid}")
    private String firstname;

    @ValidName(message = "{user.lastname.invalid}")
    private String lastname;

    @NotNull(message = "{user.birthdate.empty}")
    @Past(message = "{user.birthdate.in-future}")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthdate;

    @ValidGrade
    private Byte grade;
}
