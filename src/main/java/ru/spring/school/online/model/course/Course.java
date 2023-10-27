package ru.spring.school.online.model.course;

import jakarta.persistence.*;
import lombok.Data;
import ru.spring.school.online.model.UserFile;

import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Course {
    @Id
    private String name;

    @OneToOne(optional = false)
    private Subject subject;

    private Long price;

    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(optional = false)
    private UserFile image;

    @Temporal(TemporalType.DATE)
    private Date startTime;

    @Temporal(TemporalType.DATE)
    private Date endTime;

    @OneToMany(mappedBy = "course")
    private Set<Group> groups;
}

