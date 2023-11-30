package ru.school.authservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.school.authservice.model.Account;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenUtils {

    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;

    public JwtTokenUtils(@Value("${jwt.secret.access}") String access,
                         @Value("${jwt.secret.refresh}") String refresh
    ) {
        accessSecret = Keys.hmacShaKeyFor(access.getBytes(StandardCharsets.UTF_8));
        refreshSecret = Keys.hmacShaKeyFor(refresh.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Account account) {
        List<String> roles = account.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        LocalDateTime issuedDate = LocalDateTime.now();
        Instant expiredDate = issuedDate.plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .claim("roles", roles)
                .subject(account.getUsername())
                .expiration(Date.from(expiredDate))
                .signWith(accessSecret)
                .compact();
    }

    public String generateRefreshToken(Account account) {
        LocalDateTime issuedDate = LocalDateTime.now();
        Instant expiredDate = issuedDate.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .subject(account.getUsername())
                .expiration(Date.from(expiredDate))
                .signWith(refreshSecret)
                .compact();
    }

    public String getUsernameFromAccess(String token) {
        return getClaims(token, accessSecret).getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromAccess(String token) {
        List<?> roles = getClaims(token, accessSecret).get("roles", List.class);
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).toList();
    }

    private Claims getClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean validateToken(String token, SecretKey secret) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateAccess(String token) {
        return validateToken(token, accessSecret);
    }

    public boolean validateRefresh(String token) {
        return validateToken(token, refreshSecret);
    }
}
