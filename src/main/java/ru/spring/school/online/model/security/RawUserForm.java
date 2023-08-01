package ru.spring.school.online.model.security;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.spring.school.online.repository.UserRepository;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawUserForm {

    private UserRepository userRepo;
    @NotBlank(message = "Username shouldn't be empty")
    @Size(min = 3, max = 30, message = "Username size must be between 3 and 30 characters")
    private String username;
    @NotBlank(message = "Password shouldn't be empty")
    @Size(min = 6, message = "Password should be longer than 5 characters")
    private String password;
    private String passwordConfirm;
    @Pattern(regexp = "(ADMIN)|(STUDENT)|(TEACHER)")  //Maybe can be replaced
    private String role;

    public RawUserForm(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), User.Role.valueOf(role));
    }

    @AssertTrue(message = "This username is taken")
    public boolean isUsernameUnique() {
        return username == null || !userRepo.existsById(username);
    }

    @AssertTrue(message = "Passwords should be equals")
    public boolean isPasswordsEquals() {
        return passwordConfirm == null || password.equals(passwordConfirm);
    }

}
