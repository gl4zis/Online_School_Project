package ru.spring.school.online.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserFile {
    @Id
    private String key;
    private Long size;
    private String name;
}
