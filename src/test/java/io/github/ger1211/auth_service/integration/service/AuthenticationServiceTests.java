package io.github.ger1211.auth_service.integration.service;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.builder.AccountBuilder;
import io.github.ger1211.auth_service.controller.vo.AccountVo;
import io.github.ger1211.auth_service.model.Account;
import io.github.ger1211.auth_service.service.AuthenticationService;
import io.github.ger1211.auth_service.service.JwtService;
import io.github.ger1211.auth_service.service.dto.JwtTokenDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class AuthenticationServiceTests extends AuthServiceApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Test
    void login_withValidAccount_returnJwtToken() {
        String password = "Password123@";
        String encryptedPassword = passwordEncoder.encode(password);
        Account account = AccountBuilder.valid().withPassword(encryptedPassword).build(entityManager);

        JwtTokenDto jwtToken = authenticationService.login(new AccountVo(account.getEmail(), password));

        String token = jwtToken.getToken().substring(7);

        assertNotNull(token);
        assertTrue(jwtService.validateToken(token));
        assertThat(jwtService.getSubject(token)).isEqualTo(account.getEmail());
    }

    @Test
    void login_withBadCredentials_throwsException() {
        String email = "valid@email.com";
        String password = "BadCredentials123@";

        assertThatExceptionOfType(BadCredentialsException.class).isThrownBy(() -> authenticationService.login(new AccountVo(email, password)));
    }
}
