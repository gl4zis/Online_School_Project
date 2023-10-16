package ru.spring.school.online.model.course;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(targetEntity = Group.class, optional = false, fetch = FetchType.EAGER)
    private Group group;

    @Temporal(TemporalType.TIMESTAMP)
    private Date planedAt;

    private String name;

    private String attachmentURL;

    private String meetingURL;
}
