package ru.spring.school.online.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.spring.school.online.dto.transfer.AdminReg;
import ru.spring.school.online.dto.transfer.Login;
import ru.spring.school.online.dto.transfer.Profile;
import ru.spring.school.online.dto.transfer.StudentReg;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.validation.ValidRole;

import java.util.Date;
import java.util.Set;

@Data
public class UserDto {
    @JsonView(Profile.class)
    @NotEmpty(message = "{user.username.empty}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Size(message = "{user.username.wrong-size}", max = 20, min = 3,
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Pattern(message = "{user.username.wrong-pattern}", regexp = "^[\\w\\d]+$",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String username;

    @JsonView(Profile.class)
    @NotEmpty(message = "{user.password.empty}",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Size(message = "{user.password.wrong-size}", max = 50, min = 6,
            groups = {Login.class, StudentReg.class, AdminReg.class})
    @Pattern(message = "{user.password.wrong-pattern}", regexp = "^\\S+$",
            groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String password;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String email;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, AdminReg.class})
    @NotEmpty(message = "{user.firstname.empty}",
            groups = {StudentReg.class})
    @Size(message = "{user.firstname.wrong-size}", max = 20, min = 2,
            groups = {StudentReg.class})
    @Pattern(message = "{user.firstname.wrong-pattern}", regexp = "^[\\wа-яА-Я]+$",
            groups = {StudentReg.class})
    protected String firstname;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, AdminReg.class})
    @NotEmpty(message = "{user.lastname.empty}",
            groups = {StudentReg.class})
    @Size(message = "{user.lastname.wrong-size}", max = 20, min = 2,
            groups = {StudentReg.class})
    @Pattern(message = "{user.lastname.wrong-pattern}", regexp = "^[\\wа-яА-Я]+$",
            groups = {StudentReg.class})
    protected String lastname;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String middleName;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, AdminReg.class})
    @NotNull(message = "{user.birthdate.empty}",
            groups = {StudentReg.class})
    @Past(message = "{user.birthdate.in-future}",
            groups = {StudentReg.class})
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    protected Date dateOfBirth;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    protected String photoBase64;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class})
    @ValidRole(anyOf = {User.Role.ADMIN, User.Role.UNCONFIRMED_TEACHER},
            groups = {AdminReg.class})
    protected User.Role role;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    private Boolean locked;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    private Iterable<String> courses;


    @JsonView(Profile.class)
    @Null(groups = {Login.class, AdminReg.class})
    @NotNull(message = "{user.grade.empty}",
            groups = {StudentReg.class})
    @Min(message = "{user.grade.smaller}", value = 1,
            groups = {StudentReg.class})
    @Max(message = "{user.grade.bigger}", value = 11,
            groups = {StudentReg.class})
    private Byte studentGrade;


    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    private Set<String> teacherSubjects;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    private Set<String> teacherDiplomasBase64;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    private String teacherEducation;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    private String teacherDescription;

    @JsonView(Profile.class)
    @Null(groups = {Login.class, StudentReg.class, AdminReg.class})
    private Byte teacherWorkExperience;
}
