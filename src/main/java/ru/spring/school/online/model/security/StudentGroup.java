package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
can be just @JoinTable annotation but without additional fields
haven't decided yet
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_username", "group_id"}))
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(targetEntity = Student.class, optional = false, fetch = FetchType.EAGER)
    private Student student;
    @ManyToOne(targetEntity = Group.class, optional = false, fetch = FetchType.EAGER)
    private Group group;
    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.EAGER)
    private User addedBy;

}
