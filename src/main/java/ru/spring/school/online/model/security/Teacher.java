package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {

    @NotBlank(message = "Firstname can't be blank")
    private String firstname; //*
    @NotBlank(message = "Lastname can't be blank")
    private String lastname; //*
    private String patronymic;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide a date")
    @Past(message = "Date should be in the past")
    private Date dateOfBirth; //*
    private String photoURL; //*
    private Long phoneNumber; //*
    @Enumerated(value = EnumType.STRING)
    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull
    @Size(min = 1, max = 3, message="You must choose between 1 and 3 subjects")
    private Set<Subject> subjects; //*
    @NotBlank(message = "Education can't be blank")
    private String education; //*
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private Set<String> diplomaURLs; //*
    private String description;
    @NotNull(message = "Work experience can't be null")
    @Range(min = 0, message = "Work experience should be at least 0")
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
