package ru.spring.school.online.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.spring.school.online.dto.transfer.Profile;
import ru.spring.school.online.dto.transfer.StudentReg;

@Data
@JsonView(Profile.class)
public class StudentDto {
    @NotNull(message = "{student.grade.empty}", groups = {StudentReg.class})
    @Min(message = "{student.grade.smaller}", value = 1,
            groups = {StudentReg.class})
    @Max(message = "{student.grade.bigger}", value = 11,
            groups = {StudentReg.class})
    private Byte grade;
}
