package ru.spring.school.online.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.request.AdminOrTeacherRegDto;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.dto.request.StudentRegDto;
import ru.spring.school.online.dto.response.ProfileInfo;
import ru.spring.school.online.dto.response.StudentProfileInfo;
import ru.spring.school.online.dto.response.TeacherProfileInfo;
import ru.spring.school.online.model.course.Subject;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;

import java.util.stream.Collectors;

/**
 * All methods return null if validation error
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DtoMappingUtils {

    private final PasswordEncoder passwordEncoder;

    public Student newStudent(StudentRegDto dto) {
        if (dto.getPassword() == null)
            return null;

        Student student = new Student();
        student.setUsername(dto.getUsername());
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setFirstname(dto.getFirstname());
        student.setLastname(dto.getLastname());
        student.setBirthdate(dto.getBirthdate());
        student.setGrade(dto.getGrade());
        student.setRoles(User.Role.STUDENT);
        student.setConfirmed(true);
        return student;
    }

    public User newUser(AdminOrTeacherRegDto dto) {
        if (dto.getPassword() == null)
            return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(dto.getRoles());
        if (user.getRoles().size() == 1 && user.hasRole(User.Role.ADMIN))
            user.setConfirmed(true);
        return user;
    }

    public ProfileInfo profileFromUser(User user) {
        ProfileInfo info;
        if (user instanceof Student student)
            info = setStudentInfo(student);
        else if (user instanceof Teacher teacher)
            info = setTeacherInfo(teacher);
        else
            info = new ProfileInfo();

        info.setUsername(user.getUsername());
        info.setEmail(user.getEmail());
        info.setLocked(user.isLocked());
        info.setRoles(user.getRoles());
        info.setConfirmed(user.isConfirmed());

        info.setFirstname(user.getFirstname());
        info.setLastname(user.getLastname());
        info.setMiddleName(user.getMiddleName());
        info.setBirthdate(user.getBirthdate());
        info.setPhotoKey(user.getPhoto().getKey());

        return info;
    }

    private StudentProfileInfo setStudentInfo(Student student) {
        StudentProfileInfo info = new StudentProfileInfo();
        info.setGrade(student.getGrade());
        info.setCourses(student.getGroups()
                .stream().map(
                        group -> group.getCourse().getName()
                ).collect(Collectors.toSet()));
        return info;
    }

    private TeacherProfileInfo setTeacherInfo(Teacher teacher) {
        TeacherProfileInfo info = new TeacherProfileInfo();
        info.setSubjects(teacher.getSubjects().stream().map(Subject::getName).collect(Collectors.toSet()));
        info.setEducation(teacher.getEducation());
        info.setDescription(teacher.getDescription());
        info.setDiplomaKey(teacher.getDiploma().getKey());
        info.setWorkExperience(teacher.getWorkExperience());
        info.setCourses(teacher.getGroups()
                .stream().map(
                        group -> group.getCourse().getName()
                ).collect(Collectors.toSet()));
        return info;
    }

    public void updatedUser(User user, ProfileUpdateDto update) {
        if (user instanceof Student student)
            setStudentParams(student, update);
        else if (user instanceof Teacher teacher)
            setTeacherParams(teacher, update);

        user.setUsername(update.getUsername());
        user.setPassword(update.getPassword());
        user.setEmail(update.getEmail());

        user.setFirstname(update.getFirstname());
        user.setLastname(update.getLastname());
        user.setMiddleName(update.getMiddleName());
        user.setBirthdate(update.getBirthdate());

        user.setConfirmed(true);
    }

    private void setStudentParams(Student student, ProfileUpdateDto update) {
        student.setGrade(update.getGrade());
    }

    private void setTeacherParams(Teacher teacher, ProfileUpdateDto update) {
        teacher.setDescription(update.getDescription());
        teacher.setEducation(update.getEducation());
        teacher.setWorkExperience(update.getWorkExperience());
        teacher.setSubjects(update.getSubjects().stream().map(Subject::new).collect(Collectors.toSet()));
    }
}
