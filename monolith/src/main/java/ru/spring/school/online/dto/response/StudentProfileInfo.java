package ru.spring.school.online.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentProfileInfo extends ProfileInfo {
    private Byte grade;
}
