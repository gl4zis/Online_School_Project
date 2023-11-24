package ru.spring.school.online.model.course;


import jakarta.persistence.*;
import lombok.Data;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Teacher;

import java.util.Set;

@Entity(name = "groups")
@Data
public class Group {
    @ManyToMany(mappedBy = "groups")
    public Set<Student> students;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Teacher teacher;
    @ManyToOne(optional = false)
    private Course course;
    @OneToMany(mappedBy = "group")
    private Set<Lesson> lessons;
}
