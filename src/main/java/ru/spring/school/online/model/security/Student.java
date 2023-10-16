package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spring.school.online.model.course.Group;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

    private String firstname; //*
    private String lastname; //*
    private String middleName;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth; //*
    private Byte grade; //*

    @ManyToMany
    @JoinTable(name = "student_groups",
            joinColumns = @JoinColumn(name = "student_username"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups;
}
