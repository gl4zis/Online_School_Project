package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
public class Student extends User {
    @NotBlank(message = "Firstname can't be blank")
    private String firstname; //*
    @NotBlank(message = "Lastname can't be blank")
    private String lastname; //*
    private String patronymic;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide a date")
    @Past(message = "Date should be in the past")
    private Date dateOfBirth; //*
    @NotNull(message = "Grade can't be null")
    @Range(min = 9, max = 11, message = "Grade should be between 9 and 11")
    private Byte grade; //*
    private Long phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_purchase")
    private Set<Course> courses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<StudentGroup> studentGroups;

    public Student(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.email = user.getEmail();
    }

    @Transient
    @AssertTrue(message = "Input correct phone")
    public boolean isPhoneValid() {
        return phoneNumber == null || Pattern.matches("\\d{11,12}", phoneNumber.toString());
    }

}
