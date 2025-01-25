package io.github.ger1211.auth_service.unit.controller;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
public class AuthenticationControllerTests extends AuthServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationService authenticationService;

    @Test
    void register_withValidEmail_responseCreated() throws Exception {
        String customer = "{\"email\": \"valid@email.com\",\"password\": \"Password123@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
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
        String account = "{\"email\": \"valid@email.com\",\"password\": \"Password@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(account)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void register_withPasswordWithoutSpecialCharacter_responseBadRequest() throws Exception {
        String account = "{\"email\": \"valid@email.com\",\"password\": \"Password123\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(account)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password must contain at least one uppercase letter, one number, one special character, and be at least 8 characters long"));
    }

    @Test
    void register_withValidAccountButErrorOnService_responseBadRequest() throws Exception {
        when(authenticationService.register(any(), any())).thenThrow(new Exception());

        String accountRequest = "{\"email\": \"valid@email.com\",\"password\": \"Password123@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration")
                        .content(accountRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void registerAdmin_withValidEmail_responseCreated() throws Exception {
        String customer = "{\"email\": \"valid@email.com\",\"password\": \"Password123@\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/registration/admins")
                        .content(customer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
