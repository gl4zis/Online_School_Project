package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.Data;

/*
can be just @JoinTable annotation but without additional fields
haven't decided yet
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_username", "group_id"}))
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(targetEntity = Student.class, optional = false)
    private Student student;
    @ManyToOne(targetEntity = Group.class, optional = false)
    private Group group;
    @ManyToOne(targetEntity = User.class, optional = false)
    private User addedBy;

}
