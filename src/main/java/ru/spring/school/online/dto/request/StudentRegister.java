package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.User;

import java.util.Date;

@Data
public class StudentRegister {
    @NotEmpty(message = "{user.username.empty}")
    @Size(message = "{user.username.wrong-size}", max = 20, min = 3)
    @Pattern(message = "{user.username.wrong-pattern}", regexp = "^[\\w\\d]+$")
    private final String username;

    @NotEmpty(message = "{user.password.empty}")
    @Size(message = "{user.password.wrong-size}", max = 50, min = 6)
    @Pattern(message = "{user.password.wrong-pattern}", regexp = "^\\S+$")
    private final String password;

    @NotEmpty(message = "{user.firstname.empty}")
    @Size(message = "{user.firstname.wrong-size}", max = 20, min = 2)
    @Pattern(message = "{user.firstname.wrong-pattern}", regexp = "^[\\wа-яА-Я]+$")
    private String firstname;

    @NotEmpty(message = "{user.lastname.empty}")
    @Size(message = "{user.lastname.wrong-size}", max = 20, min = 2)
    @Pattern(message = "{user.lastname.wrong-pattern}", regexp = "^[\\wа-яА-Я]+$")
    private String lastname;

    @Past(message = "{user.birthdate.in-future}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private final Date dateOfBirth;

    @NotNull(message = "{user.grade.empty}")
    @Min(message = "{user.grade.smaller}", value = 1)
    @Max(message = "{user.grade.bigger}", value = 11)
    private final Byte grade;

    public Student toStudent(PasswordEncoder passwordEncoder) {
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(password != null ? passwordEncoder.encode(password) : null);
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setDateOfBirth(dateOfBirth);
        student.setGrade(grade);
        student.setLocked(false);
        student.setRole(User.Role.STUDENT);
        return student;
    }

}
