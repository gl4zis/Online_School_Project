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
    @EqualsAndHashCode.Include
    private String name;

    @OneToOne(optional = false)
    private Subject subject;

    private Long price;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String imageBase64;

    @Temporal(TemporalType.DATE)
    private Date startTime;

    @Temporal(TemporalType.DATE)
    private Date endTime;

    @OneToMany(mappedBy = "course")
    private Set<Group> groups;
}

