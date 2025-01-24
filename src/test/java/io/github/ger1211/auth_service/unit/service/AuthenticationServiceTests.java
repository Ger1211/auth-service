package io.github.ger1211.auth_service.unit.service;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.builder.CustomerBuilder;
import io.github.ger1211.auth_service.controller.vo.CustomerVo;
import io.github.ger1211.auth_service.model.Customer;
import io.github.ger1211.auth_service.repository.AuthenticationRepository;
import io.github.ger1211.auth_service.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTests extends AuthServiceApplicationTests {

    @MockitoBean
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void register_withValidCustomer_returnARegisteredCustomer() throws Exception {
        Customer customerWithId = CustomerBuilder.valid(1L).build();

        when(authenticationRepository.save(any())).thenReturn(customerWithId);

        Customer customerCreated = authenticationService.register(new CustomerVo(customerWithId.getEmail(), customerWithId.getPassword()));

        assertNotNull(customerCreated);
        assertThat(customerCreated.getEmail()).isEqualTo(customerWithId.getEmail());
        assertNotNull(customerCreated.getId());
    }

    @Test
    void register_withValidUser_returnACustomerWithEncryptedPassword() throws Exception {
        String plainPassword = "PlainPassword123@";
        Customer customer = CustomerBuilder.valid().withPassword(plainPassword).build();

        Mockito.when(authenticationRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Customer customerCreated = authenticationService.register(new CustomerVo(customer.getEmail(), customer.getPassword()));

        assertNotNull(customerCreated.getPassword());
        assertNotEquals(plainPassword, customerCreated.getPassword());
        assertTrue(passwordEncoder.matches(plainPassword, customerCreated.getPassword()));
    }
}
