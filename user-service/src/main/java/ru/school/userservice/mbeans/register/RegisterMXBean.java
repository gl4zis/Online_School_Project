package ru.school.userservice.mbeans.register;

import ru.school.userservice.mbeans.dto.UserInfoDto;

public interface RegisterMXBean {
    UserInfoDto getLastRegistrationInfo();
    UserInfoDto getLastLoginInfo();
    long getRegCountForLastDay();
    long getLoginCountForLastDay();
    long getUsersCount();
}
