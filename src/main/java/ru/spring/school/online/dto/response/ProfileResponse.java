package ru.spring.school.online.dto.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.spring.school.online.model.security.User;

import java.util.Date;

@Data
public class ProfileResponse {
    private String username;
    private String email;

    private String firstname;
    private String lastname;
    private String middleName;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date dateOfBirth;

    private User.Role role;
    private String photoBase64;
    private boolean locked;
    private TeacherInfo teacherInfo;
    private StudentInfo studentInfo;
}
