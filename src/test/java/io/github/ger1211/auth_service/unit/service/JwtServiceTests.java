package io.github.ger1211.auth_service.unit.service;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.builder.CustomerBuilder;
import io.github.ger1211.auth_service.model.Customer;
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
    void generateToken_withValidCustomer_returnJwtToken() {
        Customer customer = CustomerBuilder.valid().build();
        String jwtToken = jwtService.generateToken(customer);

        assertNotNull(jwtToken);
    }

    @Test
    void validateToken_withValidToken_returnTrue() {
        Customer customer = CustomerBuilder.valid().build();
        String jwtToken = jwtService.generateToken(customer);

        assertTrue(jwtService.validateToken(jwtToken));
    }

    @Test
    void validateToken_withInvalidToken_returnFalse() {
        String invalidToken = "invalid";

        assertFalse(jwtService.validateToken(invalidToken));
    }

    @Test
    void getSubject_fromToken_returnCustomerEmail() {
        Customer customer = CustomerBuilder.valid().build();
        String jwtToken = jwtService.generateToken(customer);

        assertThat(jwtService.getSubject(jwtToken)).isEqualTo(customer.getEmail());
    }

    @Test
    void isTokenExpired_withTokenNonExpired_returnFalse() {
        Customer customer = CustomerBuilder.valid().build();

        String jwtToken = jwtService.generateToken(customer);

        assertFalse(jwtService.isTokenExpired(jwtToken));
    }

    @Test
    void isTokenExpired_withTokenExpired_returnTrue() {
        Customer customer = CustomerBuilder.valid().build();

        String jwtToken = jwtService.generateToken(customer);

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
