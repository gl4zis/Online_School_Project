package ru.school.courseservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Course {
    @Id
    private String name;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String summary;
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    private Long imageId;

    @ElementCollection
    public Set<Long> studentIds;
    @Column(nullable = false)
    private Long teacherId;
    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons;
}
