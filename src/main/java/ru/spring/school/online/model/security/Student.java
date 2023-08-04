package ru.spring.school.online.model.security;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

    private String firstName; //*
    private String lastName; //*
    private String patronymic;
    private Date dateOfBirth; //*
    private Byte grade; //*
    private String email; //*
    private String photoURL;
    private Long phoneNumber;
}
