package ru.spring.school.online.model.course;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Teacher;

import java.util.Set;

@Entity(name = "groups")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(targetEntity = Teacher.class, optional = false)
    private Teacher teacher;

    @ManyToOne(targetEntity = Course.class, optional = false)
    private Course course;

    @ManyToMany(mappedBy = "groups")
    public Set<Student> students;

    @OneToMany(mappedBy = "group")
    private Set<Lesson> lessons;
}
