package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.spring.school.online.model.UserFile;
import ru.spring.school.online.model.course.Group;
import ru.spring.school.online.model.course.Subject;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Teacher extends User {

    @ManyToMany
    @JoinTable(name = "teacher_subjects",
            joinColumns = @JoinColumn(name = "teacher_username"),
            inverseJoinColumns = @JoinColumn(name = "subject_name")
    )
    private Set<Subject> subjects; //*
    private String education; //*

    @OneToOne
    private UserFile diploma; //

    @Column(columnDefinition = "text")
    private String description;
    private Byte workExperience; //*

    @OneToMany(mappedBy = "teacher")
    private Set<Group> groups;
}
