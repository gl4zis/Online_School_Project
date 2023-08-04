package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {

    private String firstName; //*
    private String lastName; //*
    private String patronymic;
    private Date dateOfBirth; //*
    private String email; //*
    private String photoURL; //*
    private Long phoneNumber; //*
    @Enumerated(value = EnumType.STRING)
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Subject> subjects; //*
    private String education; //*
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private Set<String> diplomaURLs; //*
    private String description;
    private Byte workExperience; //*
}
