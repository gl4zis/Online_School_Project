package ru.school.fileservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class JwtTokenUtils {
    private final SecretKey accesSecret;

    public JwtTokenUtils(@Value("${jwt.secret.access}") String access) {
        accesSecret = Keys.hmacShaKeyFor(access.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsernameFromAccess(String token) {
        return getClaims(token, accesSecret).getSubject();
    }

    public List<String> getRolesFromAccess(String token) {
        List<?> roles = getClaims(token, accesSecret).get("roles", List.class);
        return roles.stream().map(Object::toString).toList();
    }

    private Claims getClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(accesSecret)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
