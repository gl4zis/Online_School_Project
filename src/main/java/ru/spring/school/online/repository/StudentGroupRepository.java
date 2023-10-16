package ru.spring.school.online.repository;

import org.springframework.data.repository.CrudRepository;
import ru.spring.school.online.model.security.Group;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.StudentGroup;

import java.util.Optional;

public interface StudentGroupRepository extends CrudRepository<StudentGroup, Long> {

    void deleteStudentGroupByStudentAndGroup(Student student, Group group);

    Optional<StudentGroup> findStudentGroupByStudentAndGroup(Student student, Group group);

}
