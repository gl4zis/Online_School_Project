package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spring.school.online.model.course.Group;
import ru.spring.school.online.model.course.Subject;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {

    @ManyToMany
    @JoinTable(name = "teacher_subjects",
            joinColumns = @JoinColumn(name = "teacher_username"),
            inverseJoinColumns = @JoinColumn(name = "subject_name")
    )
    private Set<Subject> subjects; //*
    private String education; //*

    @Column(columnDefinition = "text")
    @ElementCollection
    private Set<String> diplomasBase64; //*

    @Column(columnDefinition = "text")
    private String description;
    private Byte workExperience; //*

    @OneToMany(mappedBy = "teacher")
    private Set<Group> groups;
}
