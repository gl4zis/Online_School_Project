package ru.school.courseservice.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity(name = "groups")
@Data
public class Group {
    @ElementCollection
    public Set<Long> studentIds;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;
    @Column(nullable = false)
    private Long teacherId;
    @ManyToOne(optional = false)
    private Course course;
    @OneToMany(mappedBy = "group")
    private Set<Lesson> lessons;
}
