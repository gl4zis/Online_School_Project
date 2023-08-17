package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(targetEntity = Group.class, optional = false)
    private Group group;
    @Temporal(TemporalType.TIMESTAMP)
    private Date planedAt;
    private String name;
    private String attachmentURL;
    private String meetingURL;

}
