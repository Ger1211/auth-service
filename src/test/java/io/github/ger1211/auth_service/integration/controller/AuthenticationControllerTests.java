package io.github.ger1211.auth_service.integration.controller;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.builder.CustomerBuilder;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
public class AuthenticationControllerTests extends AuthServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void login_withRegisteredValidCustomer_responseValidToken() throws Exception {
        String passwordEncrypted = passwordEncoder.encode("Password123@");
        CustomerBuilder.valid().withPassword(passwordEncrypted).build(entityManager);

        String authRequest = "{\"email\": \"valid@mail.com\",\"password\": \"Password123@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .content(authRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void login_withBadCredentials_responseBadRequest() throws Exception {
        String authRequest = "{\"email\": \"valid@mail.com\",\"password\": \"BadCredentials123@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .content(authRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
