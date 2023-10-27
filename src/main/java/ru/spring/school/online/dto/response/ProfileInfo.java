package ru.spring.school.online.dto.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.spring.school.online.model.security.User;

import java.util.Date;
import java.util.Set;

@Data
public class ProfileInfo {
    protected String username;
    protected String email;
    protected Set<User.Role> roles;
    protected Boolean locked;

    protected String firstname;
    protected String lastname;
    protected String middleName;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    protected Date birthdate;
    protected String photoKey;

    protected Set<String> courses;
}
