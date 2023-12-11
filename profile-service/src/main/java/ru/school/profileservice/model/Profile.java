package ru.school.profileservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.school.profileservice.validation.ValidName;

import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Profile {
    @Id
    private Long accountId;

    @NotNull
    @ValidName
    private String firstname; //Student|Teacher *
    @NotNull
    @ValidName
    private String lastname; //Student|Teacher *
    @ValidName
    private String middleName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-mm-dd")
    @Past
    private Date birthdate;
    private Long photoId;


    // Only teacher
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<String> subjects;

    @Column(columnDefinition = "text")
    private String description;
}
