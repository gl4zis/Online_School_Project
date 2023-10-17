package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.response.ProfileResponse;
import ru.spring.school.online.dto.response.StudentInfo;
import ru.spring.school.online.dto.response.TeacherInfo;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;

    public ProfileResponse getProfile(String username) throws UsernameNotFoundException {
        ProfileResponse response = new ProfileResponse();
        StudentInfo studentInfo = null;
        TeacherInfo teacherInfo = null;


        User user = (User) userService.loadUserByUsername(username);

        if (user.getRole() == User.Role.STUDENT)
            studentInfo = setStudentInfo((Student) user);
        if (user.getRole() == User.Role.TEACHER)
            teacherInfo = setTeacherInfo((Teacher) user);

        response.setUsername(username);
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setMiddleName(user.getMiddleName());
        response.setEmail(user.getEmail());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setPhotoBase64(user.getPhotoBase64());
        response.setRole(user.getRole());
        response.setLocked(user.isLocked());
        response.setStudentInfo(studentInfo);
        response.setTeacherInfo(teacherInfo);

        return response;
    }

    private StudentInfo setStudentInfo(Student student) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setGrade(student.getGrade());
        //TODO: Don't forget
        // studentInfo.setCourses(...);
        return studentInfo;
    }

    private TeacherInfo setTeacherInfo(Teacher teacher) {
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setSubjects(teacher.getSubjects());
        teacherInfo.setEducation(teacher.getEducation());
        teacherInfo.setDiplomasBase64(teacher.getDiplomasBase64());
        teacherInfo.setDescription(teacher.getDescription());
        teacherInfo.setWorkExperience(teacher.getWorkExperience());
        //TODO: Don't forget
        // teacherInfo.setCourses(...);
        return teacherInfo;
    }
}
