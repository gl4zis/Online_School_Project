package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "subjects")
    private Set<User> users;
}
