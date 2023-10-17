package ru.spring.school.online.model.course;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne
    private Subject subject;

    private String name;

    private Long price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String imageBase64;

    @Temporal(TemporalType.DATE)
    private Date startTime;

    @Temporal(TemporalType.DATE)
    private Date endTime;

    @OneToMany(mappedBy = "course")
    private Set<Group> groups;
}

