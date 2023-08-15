package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(targetEntity = Subject.class, optional = false)
    private Subject subject;
    private String name;
    private Long price;
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;
    @OneToMany(mappedBy = "course")
    private Set<Group> groups;
}

