package ru.school.lessonservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long courseId;
    @Column(nullable = false)
    private Date planedAt;
    @Column(nullable = false)
    private String title;
    private Long attachmentFileId;
    private String meetingUrl;
    private Long testId;
}
