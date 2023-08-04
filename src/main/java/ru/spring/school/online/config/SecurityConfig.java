package ru.spring.school.online.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    Further change requestMatchers(AntPathRequestMatcher.antMatcher("/register/**")).hasRole("ADMIN")
    Useless for now
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/callback/"),
                                AntPathRequestMatcher.antMatcher("/webjars/**"),
                                AntPathRequestMatcher.antMatcher("/error**"),
                                AntPathRequestMatcher.antMatcher("/css/**"))
                        .permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/register"),
                                AntPathRequestMatcher.antMatcher("/login"),
                                AntPathRequestMatcher.antMatcher("/register/continue"))
                        .anonymous()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/admin"),
                                AntPathRequestMatcher.antMatcher("/admin/**"))
                        .hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> {
                    form.loginPage("/login");
                    form.defaultSuccessUrl("/profile");
                })
                .getOrBuild();
    }
}
