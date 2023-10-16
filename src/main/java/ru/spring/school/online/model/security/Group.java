package ru.spring.school.online.model.security;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@Table(name = "groups")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(targetEntity = Teacher.class, optional = false, fetch = FetchType.EAGER)
    private Teacher teacher;
    @ManyToOne(targetEntity = Course.class, optional = false, fetch = FetchType.EAGER)
    private Course course;
    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    private User createdBy;
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    public Set<StudentGroup> studentGroups;
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<Lesson> lessons;
}
