package ru.spring.school.online.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentRegister {
    private final String username;
    private final String password;
    private String firstname;
    private String lastname;
    private final Date dateOfBirth;
    private final Byte grade;
}
