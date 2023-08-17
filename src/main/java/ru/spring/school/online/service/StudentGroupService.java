package ru.spring.school.online.service;

import org.springframework.stereotype.Service;
import ru.spring.school.online.exception.IdNotFoundException;
import ru.spring.school.online.model.security.Group;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.StudentGroup;
import ru.spring.school.online.repository.StudentGroupRepository;

import java.util.Optional;

@Service
public class StudentGroupService {
    final StudentGroupRepository studentGroupRepo;

    public StudentGroupService(StudentGroupRepository studentGroupRepo) {
        this.studentGroupRepo = studentGroupRepo;
    }

    public StudentGroup findStudentGroup(Student student, Group group) {
        Optional<StudentGroup> studentGroupOpt = studentGroupRepo.findStudentGroupByStudentAndGroup(student, group);
        if (studentGroupOpt.isPresent()) {
            return studentGroupOpt.get();
        }
        throw new IdNotFoundException(String.format("Student group with student %s and group %d not found", student.getUsername(), group.getId()));
    }

    public void saveStudentGroup(StudentGroup studentGroup) {
        studentGroupRepo.save(studentGroup);
    }

    public boolean deleteStudentGroup(Student student, Group group) {
        if (studentGroupRepo.findStudentGroupByStudentAndGroup(student, group)
                .isPresent()) {
            studentGroupRepo.deleteStudentGroupByStudentAndGroup(student, group);
            return true;
        }
        return false;
    }
}
