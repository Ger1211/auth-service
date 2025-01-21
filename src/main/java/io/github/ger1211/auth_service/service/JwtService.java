package io.github.ger1211.auth_service.service;

import io.github.ger1211.auth_service.model.Customer;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY= Jwts.SIG.HS256.key().build();
    private final Date TEN_HOURS = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);

    public String generateToken(Customer customer) {
        return Jwts.builder()
                .subject(customer.getEmail())
                .issuedAt(new Date())
                .expiration(TEN_HOURS)
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
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
