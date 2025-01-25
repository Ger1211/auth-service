package io.github.ger1211.auth_service.unit.service;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTests extends AuthServiceApplicationTests {

    @Autowired
    private JwtService jwtService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("jwt.token.expiration", () -> "1000");
    }

    @Test
    void generateToken_withValidEmail_returnValidJwtToken() {
        String email = "valid@mail.com";
        String jwtToken = jwtService.generateToken(email);

        assertNotNull(jwtToken);
    }

    @Test
    void validateToken_withValidToken_returnTrue() {
        String email = "valid@mail.com";
        String jwtToken = jwtService.generateToken(email);

        assertTrue(jwtService.validateToken(jwtToken));
    }

    @Test
    void validateToken_withInvalidToken_returnFalse() {
        String invalidToken = "invalid";

        assertFalse(jwtService.validateToken(invalidToken));
    }

    @Test
    void getSubject_fromToken_returnCustomerEmail() {
        String email = "valid@mail.com";
        String jwtToken = jwtService.generateToken(email);

        assertThat(jwtService.getSubject(jwtToken)).isEqualTo(email);
    }

    @Test
    void isTokenExpired_withTokenNonExpired_returnFalse() {
        String email = "valid@mail.com";
        String jwtToken = jwtService.generateToken(email);

        assertFalse(jwtService.isTokenExpired(jwtToken));
    }

    @Test
    void isTokenExpired_withTokenExpired_returnTrue() {
        String email = "valid@mail.com";
        String jwtToken = jwtService.generateToken(email);

        waitOneSecond();

        assertTrue(jwtService.isTokenExpired(jwtToken));
    }

    void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
