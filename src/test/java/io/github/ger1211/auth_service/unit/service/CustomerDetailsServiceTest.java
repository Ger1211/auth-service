package io.github.ger1211.auth_service.unit.service;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.builder.CustomerBuilder;
import io.github.ger1211.auth_service.model.Customer;
import io.github.ger1211.auth_service.repository.AuthenticationRepository;
import io.github.ger1211.auth_service.service.CustomerDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CustomerDetailsServiceTest extends AuthServiceApplicationTests {

    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private CustomerDetailsService customerDetailsService;

    @Test
    public void loadUserByUsername_withValidCustomer_returnUserDetail() {
        Customer customer = CustomerBuilder.valid().build();

        when(authenticationRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        UserDetails userDetails = customerDetailsService.loadUserByUsername(customer.getEmail());

        assertThat(userDetails.getUsername()).isEqualTo(customer.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(customer.getPassword());
    }

    @Test
    public void loadUserByUsername_withNonExistentEmail_throwsException() {
        String nonExistentEmail = "nonExistent@mail.com";
        when(authenticationRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> customerDetailsService.loadUserByUsername(nonExistentEmail))
                .withMessageContaining(nonExistentEmail);
    }
}
