package ru.spring.school.online.model.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RawUserForm {

    protected String username;
    protected String password;

    protected String firstname;
    protected String lastname;

    protected Integer age;
    protected Integer classNumber;
    protected String photo;  //Will be realised in future
    protected String description;
    protected Long phoneNumber;
    protected String email;

    protected String role;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), firstname, lastname,
                age, classNumber, photo, description, phoneNumber, email, User.Role.valueOf(role));
    }
}
