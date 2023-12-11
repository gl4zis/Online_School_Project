package ru.school.authservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.school.JwtTokenUtils;
import ru.school.authservice.model.Account;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class JwtGenerator {
    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;
    private final JwtTokenUtils jwtTokenUtils;

    public JwtGenerator(@Value("${jwt.secret.refresh}") String refresh,
                        JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
        accessSecret = jwtTokenUtils.getAccess();
        refreshSecret = Keys.hmacShaKeyFor(refresh.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Account account) {
        List<String> roles = account.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        LocalDateTime issuedDate = LocalDateTime.now();
        Instant expiredDate = issuedDate.plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .claim("roles", roles)
                .subject(account.getId().toString())
                .expiration(Date.from(expiredDate))
                .signWith(accessSecret)
                .compact();
    }

    public String generateRefreshToken(Account account) {
        LocalDateTime issuedDate = LocalDateTime.now();
        Instant expiredDate = issuedDate.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .subject(account.getId().toString())
                .expiration(Date.from(expiredDate))
                .signWith(refreshSecret)
                .compact();
    }

    public boolean validateRefresh(String token) {
        return jwtTokenUtils.validateToken(token, refreshSecret);
    }
}
