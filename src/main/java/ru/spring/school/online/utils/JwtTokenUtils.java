package ru.spring.school.online.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.spring.school.online.model.security.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class JwtTokenUtils {

    private final SecretKey accesSecret;
    private final SecretKey refreshSecret;

    public JwtTokenUtils(@Value("${jwt.secret.access}") String access,
                         @Value("${jwt.secret.refresh}") String refresh
    ) {
        accesSecret = Keys.hmacShaKeyFor(access.getBytes(StandardCharsets.UTF_8));
        refreshSecret = Keys.hmacShaKeyFor(refresh.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        List<String> roles = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        LocalDateTime issuedDate = LocalDateTime.now();
        Instant expiredDate = issuedDate.plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .claim("roles", roles)
                .subject(user.getUsername())
                .expiration(Date.from(expiredDate))
                .signWith(accesSecret)
                .compact();
    }

    public String generateRefreshToken(User user) {
        LocalDateTime issuedDate = LocalDateTime.now();
        Instant expiredDate = issuedDate.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(Date.from(expiredDate))
                .signWith(refreshSecret)
                .compact();
    }

    public String getUsernameFromAccess(String token) {
        return getClaims(token, accesSecret).getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromAccess(String token) {
        List<?> roles = getClaims(token, accesSecret).get("roles", List.class);
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
        } catch (ExpiredJwtException expEx) {
            log.debug("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.debug("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.debug("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.debug("Invalid signature", sEx);
        } catch (Exception e) {
            log.debug("invalid token", e);
        }
        return false;
    }

    public boolean validateAccess(String token) {
        return validateToken(token, accesSecret);
    }
}
