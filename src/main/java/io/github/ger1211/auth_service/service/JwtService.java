package io.github.ger1211.auth_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY= Jwts.SIG.HS256.key().build();
    private final long expirationTimeInMillis;

    public JwtService(@Value("${jwt.token.expiration:60000}") long expirationTimeInMillis) {
        this.expirationTimeInMillis = expirationTimeInMillis;
    }

    public String generateToken(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeInMillis);
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        return this.extractClaims(token)
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        try {
            extractClaims(token).getExpiration();
            return false;
        } catch (ExpiredJwtException exception) {
            return true;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
