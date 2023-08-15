package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.Data;

/*
can be just @JoinTable annotation but without additional fields
haven't decided yet
 */
@Entity
@Data
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Group group;
    @ManyToOne
    private User addedBy;

}
