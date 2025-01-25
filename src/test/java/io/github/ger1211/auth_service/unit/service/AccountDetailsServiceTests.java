package io.github.ger1211.auth_service.unit.service;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.builder.AccountBuilder;
import io.github.ger1211.auth_service.model.Account;
import io.github.ger1211.auth_service.repository.AuthenticationRepository;
import io.github.ger1211.auth_service.service.AccountDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AccountDetailsServiceTests extends AuthServiceApplicationTests {

    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private AccountDetailsService accountDetailsService;

    @Test
    void loadUserByUsername_withValidCustomer_returnUserDetail() {
        Account account = AccountBuilder.validCustomer().build();

        when(authenticationRepository.findByEmail(account.getEmail())).thenReturn(Optional.of(account));

        UserDetails userDetails = accountDetailsService.loadUserByUsername(account.getEmail());

        assertThat(userDetails.getUsername()).isEqualTo(account.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(account.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(account.getRole().name())));
    }

    @Test
    void loadUserByUsername_withNonExistentEmail_throwsException() {
        String nonExistentEmail = "nonExistent@mail.com";
        when(authenticationRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> accountDetailsService.loadUserByUsername(nonExistentEmail))
                .withMessageContaining(nonExistentEmail);
    }
}
