package ru.spring.school.online.model.course;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Group group;

    private Date planedAt;

    private String title;

    @Column(columnDefinition = "text")
    private String attachmentFileBase64;

    private String meetingUrl;
}
