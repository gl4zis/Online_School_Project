package ru.school.courseservice.model;

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
    private Course course;
    @Column(nullable = false)
    private Date planedAt;
    @Column(nullable = false)
    private String title;
    private Long attachmentFileId;
    private String meetingUrl;
}
