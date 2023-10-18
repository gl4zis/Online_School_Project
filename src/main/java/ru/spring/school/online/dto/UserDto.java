package ru.spring.school.online.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.spring.school.online.dto.transfer.*;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.validation.ValidRole;

import java.util.Date;

@Data
@JsonView(Profile.class)
public class UserDto {
    @NotEmpty(message = "{user.username.empty}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Size(message = "{user.username.wrong-size}", max = 20, min = 3,
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Pattern(message = "{user.username.wrong-pattern}", regexp = "^[\\w\\d]+$",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String username;

    @JsonView(SecretInfo.class)
    @NotEmpty(message = "{user.password.empty}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Size(message = "{user.password.wrong-size}", max = 50, min = 6,
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Pattern(message = "{user.password.wrong-pattern}", regexp = "^\\S+$",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String password;

    @Null(message = "{user.email.null}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String email;

    @Null(message = "{user.firstname.null}",
            groups = {Login.class, AdminReg.class})
    @NotEmpty(message = "{user.firstname.empty}",
            groups = {StudentReg.class})
    @Size(message = "{user.firstname.wrong-size}", max = 20, min = 2,
            groups = {StudentReg.class})
    @Pattern(message = "{user.firstname.wrong-pattern}", regexp = "^[\\wа-яА-Я]+$",
            groups = {StudentReg.class})
    protected String firstname;

    @Null(message = "{user.lastname.null}",
            groups = {Login.class, AdminReg.class})
    @NotEmpty(message = "{user.lastname.empty}",
            groups = {StudentReg.class})
    @Size(message = "{user.lastname.wrong-size}", max = 20, min = 2,
            groups = {StudentReg.class})
    @Pattern(message = "{user.lastname.wrong-pattern}", regexp = "^[\\wа-яА-Я]+$",
            groups = {StudentReg.class})
    protected String lastname;

    @Null(message = "{user.middle-name.null}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String middleName;

    @Null(message = "{user.birthdate.null}",
            groups = {Login.class, AdminReg.class})
    @NotNull(message = "{user.birthdate.empty}",
            groups = {StudentReg.class})
    @Past(message = "{user.birthdate.in-future}",
            groups = {StudentReg.class})
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    protected Date dateOfBirth;

    @Null(message = "{user.photo.null}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String photoBase64;

    @Null(message = "{user.role.null}",
            groups = {Login.class, StudentReg.class})
    @ValidRole(message = "{user.role.invalid}", anyOf = {User.Role.ADMIN, User.Role.UNCONFIRMED_TEACHER},
            groups = {AdminReg.class})
    protected User.Role role;

    @Null(message = "{user.locked.null}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    private Boolean locked;

    @Null(message = "{user.courses.null}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    private Iterable<String> courses;

    @Null(message = "{teacher.info.null}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Valid
    private TeacherDto teacherInfo;

    @Null(message = "{student.info.null}",
            groups = {Login.class, AdminReg.class})
    @NotNull(message = "{student.info.empty}",
            groups = {StudentReg.class})
    @Valid
    private StudentDto studentInfo;
}
