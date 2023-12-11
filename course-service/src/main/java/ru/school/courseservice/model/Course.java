package ru.school.courseservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String description;
    private Long imageId;

    @OneToMany(mappedBy = "course")
    private Set<Group> groups;
}
