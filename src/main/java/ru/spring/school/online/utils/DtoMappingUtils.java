package ru.spring.school.online.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.request.AdminOrTeacherRegDto;
import ru.spring.school.online.dto.request.StudentRegDto;
import ru.spring.school.online.dto.response.ProfileInfo;
import ru.spring.school.online.dto.response.StudentProfileInfo;
import ru.spring.school.online.dto.response.TeacherProfileInfo;
import ru.spring.school.online.model.course.Subject;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.CourseService;

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

    public Student student(StudentRegDto dto) {
        if (dto.getPassword() == null)
            return null;

        Student student = new Student();
        student.setUsername(dto.getUsername());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setFirstname(dto.getFirstname());
        student.setLastname(dto.getLastname());
        student.setBirthdate(dto.getBirthdate());
        student.setGrade(dto.getGrade());
        student.setLocked(false);
        student.setRole(User.Role.STUDENT);
        return student;
    }

    public User user(AdminOrTeacherRegDto dto) {
        if (dto.getPassword() == null)
            return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setLocked(false);
        return user;
    }

    public ProfileInfo profileFromUser(User user) {
        ProfileInfo info;
        if (user.getRole() == User.Role.STUDENT)
            info = setStudentInfo((Student) user);
        else if (Set.of(User.Role.UNCONFIRMED_TEACHER, User.Role.TEACHER).contains(user.getRole()))
            info = setTeacherInfo((Teacher) user);
        else
            info = new ProfileInfo();

        info.setUsername(user.getUsername());
        info.setEmail(user.getEmail());
        info.setLocked(user.isLocked());
        info.setRole(user.getRole());

        info.setFirstname(user.getFirstname());
        info.setLastname(user.getLastname());
        info.setMiddleName(user.getMiddleName());
        info.setBirthdate(user.getBirthdate());
        info.setPhotoBase64(user.getPhotoBase64());

        return info;
    }

    private StudentProfileInfo setStudentInfo(Student student) {
        StudentProfileInfo info = new StudentProfileInfo();
        info.setGrade(student.getGrade());
        info.setCourses(courseService.getStudentCourseNames(student.getUsername()));
        return info;
    }

    private TeacherProfileInfo setTeacherInfo(Teacher teacher) {
        TeacherProfileInfo info = new TeacherProfileInfo();
        info.setSubjects(teacher.getSubjects().stream().map(Subject::getName).collect(Collectors.toSet()));
        info.setEducation(teacher.getEducation());
        info.setDescription(teacher.getDescription());
        info.setDiplomasBase64(teacher.getDiplomasBase64());
        info.setWorkExperience(teacher.getWorkExperience());
        info.setCourses(courseService.getTeacherCourseNames(teacher.getUsername()));
        return info;
    }
}
