package ru.school.userservice.mbeans.register;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import ru.school.userservice.mbeans.dto.UserInfoDto;
import ru.school.userservice.model.User;
import ru.school.userservice.service.UserService;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.*;

@ManagedResource
@Component
@RequiredArgsConstructor
public class Register extends NotificationBroadcasterSupport implements RegisterMXBean {
    private final static long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;
    private final static Logger LOGGER = LoggerFactory.getLogger(Register.class);

    private final UserService userService;

    private int notificationSeq = 0;

    private final List<Long> timestampsOfRegistrations = new ArrayList<>();
    private final List<Long> timestampsOfLogins = new ArrayList<>();
    private UserInfoDto lastRegistrationInfo = null;
    private UserInfoDto lastLoginInfo = null;


    public void onRegister(User user) {
        LOGGER.info("Registration");

        lastRegistrationInfo = new UserInfoDto(user);
        timestampsOfRegistrations.add(lastRegistrationInfo.getTimestamp());
        int index = 0;
        long previousDay = System.currentTimeMillis() - MILLIS_IN_DAY;
        while (index < timestampsOfRegistrations.size() && timestampsOfRegistrations.get(index) < previousDay)
            index++;

        if (index > 0) timestampsOfRegistrations.subList(0, index).clear();

        Notification notification = new Notification("info", this, notificationSeq++,
                lastRegistrationInfo.getTimestamp(), "New user \"" + lastRegistrationInfo.getUsername() + "\" registered");
        sendNotification(notification);
    }

    public void onLogin(User user) {
        LOGGER.info("Login");

        lastLoginInfo = new UserInfoDto(user);
        timestampsOfLogins.add(lastLoginInfo.getTimestamp());
        int index = 0;
        long previousDay = System.currentTimeMillis() - MILLIS_IN_DAY;
        while (index < timestampsOfLogins.size()&& timestampsOfLogins.get(index) < previousDay)
            index++;

        if (index > 0) timestampsOfLogins.subList(0, index).clear();

        Notification notification = new Notification("info", this, notificationSeq++,
                lastLoginInfo.getTimestamp(), "User \"" + lastLoginInfo.getUsername() + "\" signed in");
        sendNotification(notification);
    }

    @Override
    @ManagedAttribute
    public UserInfoDto getLastRegistrationInfo() {
        return lastRegistrationInfo;
    }

    @Override
    @ManagedAttribute
    public UserInfoDto getLastLoginInfo() {
        return lastLoginInfo;
    }

    @Override
    @ManagedAttribute
    public long getRegCountForLastDay() {
        long count = 0;
        long previousDay = System.currentTimeMillis() - MILLIS_IN_DAY;
        for (Long timestamp : timestampsOfRegistrations) {
            if (timestamp >= previousDay) count++;
        }
        return count;
    }

    @Override
    @ManagedAttribute
    public long getLoginCountForLastDay() {
        long count = 0;
        long previousDay = System.currentTimeMillis() - MILLIS_IN_DAY;
        for (Long timestamp : timestampsOfLogins) {
            if (timestamp >= previousDay) count++;
        }
        return count;
    }

    @Override
    @ManagedAttribute
    public long getUsersCount() {
        return ((Collection<?>)userService.getAll()).size();
    }
}
