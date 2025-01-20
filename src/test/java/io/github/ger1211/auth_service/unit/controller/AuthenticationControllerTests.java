package io.github.ger1211.auth_service.unit.controller;

import io.github.ger1211.auth_service.config.SecurityConfig;
import io.github.ger1211.auth_service.controller.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AuthenticationController.class)
@ContextConfiguration(classes= { SecurityConfig.class, AuthenticationController.class })
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register_withValidEmail_responseOk() throws Exception {
        String customer = "{\"email\": \"valid@email.com\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void register_withoutEmail_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void register_withInvalidEmail_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"invalid\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
