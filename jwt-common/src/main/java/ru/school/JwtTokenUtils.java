package ru.school;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtTokenUtils {
    private static final String ACCESS = "aa7sr4lm21fr5fq0y0qyhjv8221r8ro3ee1iwj6sj4i1qppjin";
    private final SecretKey accessSecret;

    public JwtTokenUtils() {
        accessSecret = Keys.hmacShaKeyFor(ACCESS.getBytes(StandardCharsets.UTF_8));
    }

    public SecretKey getAccess() {
        return accessSecret;
    }

    public String getUsernameFromAccess(String token) {
        return getClaims(token, accessSecret).getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromAccess(String token) {
        List<?> roles = getClaims(token, accessSecret).get("roles", List.class);
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).toList();
    }

    public boolean accessHasRole(String token, String roleName) {
        List<SimpleGrantedAuthority> roles = getRolesFromAccess(token);
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equals(roleName))
                return true;
        }
        return false;
    }

    private Claims getClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, SecretKey secret) {
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

    public String getAccessToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer "))
            return token.substring(7);
        return null;
    }
}
