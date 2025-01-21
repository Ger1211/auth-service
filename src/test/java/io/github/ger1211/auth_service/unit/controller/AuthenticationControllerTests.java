package io.github.ger1211.auth_service.unit.controller;

import io.github.ger1211.auth_service.config.SecurityConfig;
import io.github.ger1211.auth_service.controller.AuthenticationController;
import io.github.ger1211.auth_service.service.CustomerDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthenticationController.class)
@ContextConfiguration(classes= { SecurityConfig.class, AuthenticationController.class })
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerDetailsService customerDetailsService;

    @Test
    void register_withValidEmail_responseOk() throws Exception {
        String customer = "{\"email\": \"valid@email.com\",\"password\": \"Password123@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void register_withoutEmail_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email must not be empty"));
    }

    @Test
    void register_withInvalidEmail_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"invalid\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email is invalid"));
    }

    @Test
    void register_withoutPassword_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"valid@email.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must not be empty"));
    }

    @Test
    void register_withPasswordWithLessThan8Characters_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"valid@email.com\",\"password\": \"pass\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void register_withPasswordWithoutUppercaseLetter_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"valid@email.com\",\"password\": \"password123@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void register_withPasswordWithoutNumber_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"valid@email.com\",\"password\": \"Password@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void register_withPasswordWithoutSpecialCharacter_responseBadRequest() throws Exception {
        String customer = "{\"email\": \"valid@email.com\",\"password\": \"Password123\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must contain at least one uppercase letter, one number, one special character, and be at least 8 characters long"));
    }
}
