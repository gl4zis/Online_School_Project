package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcType;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(targetEntity = Subject.class, optional = false, fetch = FetchType.EAGER)
    private Subject subject;
    private String name;
    private Long price;
    @Column(columnDefinition="TEXT")
    private String description;
    private String photoURL;
    @Temporal(TemporalType.DATE)
    private Date startTime;
    @Temporal(TemporalType.DATE)
    private Date endTime;
    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private Set<Student> students;
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Group> groups;
}

