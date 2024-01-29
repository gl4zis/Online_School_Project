package ru.school.courseservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String summary;
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    private String imageId;
    @Column(nullable = false)
    private boolean published;

    @ElementCollection
    public Set<Long> studentIds;
    @Column(nullable = false)
    private Long teacherId;
    @ElementCollection
    private Set<Long> lessonIds;
}
