package ru.spring.school.online.model.security;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(targetEntity = Teacher.class, optional = false)
    private Teacher teacher;
    @ManyToOne(targetEntity = Course.class, optional = false)
    private Course course;
    @ManyToOne(targetEntity = User.class, optional = false)
    private User createdBy;
    @OneToMany(mappedBy = "group")
    public Set<StudentGroup> studentGroups;
    @OneToMany(mappedBy = "group")
    private Set<Lesson> lessons;
}
