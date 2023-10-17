package ru.spring.school.online.model.course;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Subject {
    @Id
    private String name;
}
