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

    @ManyToOne(targetEntity = Group.class, optional = false)
    private Group group;

    private Date planedAt;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String attachmentFileBase64;

    private String meetingURL;
}
