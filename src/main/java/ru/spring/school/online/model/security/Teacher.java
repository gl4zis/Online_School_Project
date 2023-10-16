package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spring.school.online.model.course.Group;
import ru.spring.school.online.model.course.Subject;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {

    private String firstname; //*
    private String lastname; //*
    private String middleName;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @ManyToMany
    @JoinTable(name = "teacher_subjects",
            joinColumns = @JoinColumn(name = "teacher_username"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects; //*
    private String education; //*

    @ElementCollection(targetClass = String.class)
    private Set<String> diplomaURLs; //*

    @Column(columnDefinition = "TEXT")
    private String description;
    private Byte workExperience; //*

    @OneToMany(mappedBy = "teacher")
    private Set<Group> groups;
}
