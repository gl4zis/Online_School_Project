package ru.spring.school.online.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.UserDto;
import ru.spring.school.online.model.course.Subject;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.CourseService;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

/**
 * All methods return null if validation error
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DtoMappingUtils {

    private final PasswordEncoder passwordEncoder;
    private final CourseService courseService;

    public Student userDtoToStudent(UserDto dto) {
        if (dto.getPassword() == null)
            return null;

        Student student = new Student();
        student.setUsername(dto.getUsername());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setFirstname(dto.getFirstname());
        student.setLastname(dto.getLastname());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGrade(dto.getStudentGrade());
        student.setLocked(false);
        student.setRole(User.Role.STUDENT);
        return student;
    }

    public User userDtoToUser(UserDto dto) {
        if (dto.getPassword() == null)
            return null;

        try {
            User user = dto.getRole().getUserClass().getConstructor().newInstance();
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRole(dto.getRole());
            user.setLocked(false);
            return user;
        } catch (NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException e) {
            log.error("Error while creating object: " + e.getMessage());
            return null;
        }
    }

    public UserDto profileFromUser(User user) {
        UserDto dto;
        if (user.getRole() == User.Role.STUDENT)
            dto = setStudentDto((Student) user);
        else if (user.getRole() == User.Role.TEACHER)
            dto = setTeacherDto((Teacher) user);
        else
            dto = new UserDto();

        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setMiddleName(user.getMiddleName());
        dto.setEmail(user.getEmail());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setPhotoBase64(user.getPhotoBase64());
        dto.setRole(user.getRole());
        dto.setLocked(user.isLocked());

        return dto;
    }

    private UserDto setStudentDto(Student student) {
        UserDto dto = new UserDto();
        dto.setStudentGrade(student.getGrade());
        dto.setCourses(courseService.getStudentCourseNames(student.getUsername()));
        return dto;
    }

    private UserDto setTeacherDto(Teacher teacher) {
        UserDto dto = new UserDto();
        dto.setTeacherSubjects(teacher.getSubjects().stream().map(Subject::getName).collect(Collectors.toSet()));
        dto.setTeacherEducation(teacher.getEducation());
        dto.setTeacherDiplomasBase64(teacher.getDiplomasBase64());
        dto.setTeacherDescription(teacher.getDescription());
        dto.setTeacherWorkExperience(teacher.getWorkExperience());
        dto.setCourses(courseService.getTeacherCourseNames(teacher.getUsername()));
        return dto;
    }
}
