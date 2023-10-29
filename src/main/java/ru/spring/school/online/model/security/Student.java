package ru.spring.school.online.model.security;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.spring.school.online.model.course.Group;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Student extends User {

    private Byte grade; //*

    @ManyToMany
    @JoinTable(name = "student_groups",
            joinColumns = @JoinColumn(name = "student_username"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups;
}
