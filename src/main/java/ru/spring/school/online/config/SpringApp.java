package ru.spring.school.online.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.spring.school.online.model.security.Student;

@SpringBootApplication(scanBasePackages = "ru.spring.school.online")
@EntityScan("ru.spring.school.online.model")
@EnableJpaRepositories("ru.spring.school.online.repository")
public class SpringApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }
}
