package ru.spring.school.online.model.course;

import jakarta.persistence.*;
import lombok.Data;
import ru.spring.school.online.model.security.Teacher;

import java.util.Set;

@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "subjects")
    private Set<Teacher> teachers;
}
