package ru.spring.school.online.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.StudentDto;
import ru.spring.school.online.dto.TeacherDto;
import ru.spring.school.online.dto.UserDto;
import ru.spring.school.online.model.course.Subject;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.CourseService;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
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
        if (dto.getPassword() == null || dto.getStudentInfo() == null)
            return null;

        Student student = new Student();
        student.setUsername(dto.getUsername());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setFirstname(dto.getFirstname());
        student.setLastname(dto.getLastname());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGrade(dto.getStudentInfo().getGrade());
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
        UserDto dto = new UserDto();
        if (user.getRole() == User.Role.STUDENT)
            setStudentDto((Student) user, dto);
        else if (Set.of(User.Role.TEACHER, User.Role.UNCONFIRMED_TEACHER).contains(user.getRole()))
            setTeacherDto((Teacher) user, dto);

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

    private void setStudentDto(Student student, UserDto dto) {
        StudentDto studentDto = new StudentDto();
        studentDto.setGrade(student.getGrade());

        dto.setStudentInfo(studentDto);
        dto.setCourses(courseService.getStudentCourseNames(student.getUsername()));
    }

    private void setTeacherDto(Teacher teacher, UserDto dto) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSubjects(teacher.getSubjects().stream().map(Subject::getName).collect(Collectors.toSet()));
        teacherDto.setEducation(teacher.getEducation());
        teacherDto.setDiplomasBase64(teacher.getDiplomasBase64());
        teacherDto.setDescription(teacher.getDescription());
        teacherDto.setWorkExperience(teacher.getWorkExperience());

        dto.setTeacherInfo(teacherDto);
        dto.setCourses(courseService.getTeacherCourseNames(teacher.getUsername()));
    }
}
