package io.github.bhuyanp.restapp.util;

import io.github.bhuyanp.restapp.config.TokenConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 *
 * @author Prasanta Bhuyan(mailto:prasanta.k.bhuyan@gmail.com)
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final TokenConfig tokenConfig;

    public String generateToken(UserDetails userDetails) {
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenConfig.getSecret().getBytes());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000)) // 5 hours
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenConfig.getSecret().getBytes());
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token).getPayload();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
