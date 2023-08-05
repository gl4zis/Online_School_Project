package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {

    private String firstname; //*
    private String lastname; //*
    private String patronymic;
    private Date dateOfBirth; //*
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

    public Teacher(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.email = user.getEmail();
    }

    @Transient
    @AssertTrue(message = "Input correct phone")
    public boolean isPhoneValid() {
        return phoneNumber != null && Pattern.matches("\\d{11,12}", phoneNumber.toString());
    }
}
