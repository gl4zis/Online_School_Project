package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;

import java.lang.reflect.InvocationTargetException;

@Data
@Slf4j
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
        return getDefaultUser(passwordEncoder, User.class, User.Role.ADMIN);
    }

    public Teacher toTeacher(PasswordEncoder passwordEncoder) {
        return (Teacher) getDefaultUser(passwordEncoder, Teacher.class, User.Role.UNCONFIRMED_TEACHER);
    }

    private User getDefaultUser(PasswordEncoder passwordEncoder, Class<? extends User> userClass, User.Role role) {
        try {
            User user = userClass.getDeclaredConstructor().newInstance();
            user.setUsername(username);
            user.setPassword(password != null ? passwordEncoder.encode(password) : null);
            user.setRole(role);
            user.setLocked(false);
            return user;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            log.error("Error while creating object: " + e.getMessage());
            return null;
        }
    }
}
