package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;

@Data
public class AuthRequest {
    @NotEmpty(message = "{user.username.empty}")
    @Size(message = "{user.username.wrong-size}", max = 20, min = 3)
    @Pattern(message = "{user.username.wrong-pattern}", regexp = "^[\\w\\d]+$")
    private final String username;

    @NotEmpty(message = "{user.password.empty}")
    @Size(message = "{user.password.wrong-size}", max = 50, min = 6)
    @Pattern(message = "{user.password.wrong-pattern}", regexp = "^\\S+$")
    private final String password;

    public User toAdmin(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password != null ? passwordEncoder.encode(password) : null);
        user.setRole(User.Role.ADMIN);
        user.setLocked(false);
        return user;
    }

    public Teacher toTeacher(PasswordEncoder passwordEncoder) {
        Teacher teacher = new Teacher();
        teacher.setUsername(username);
        teacher.setPassword(password != null ? passwordEncoder.encode(password) : null);
        teacher.setRole(User.Role.UNCONFIRMED_TEACHER);
        teacher.setLocked(false);
        return teacher;
    }
}
