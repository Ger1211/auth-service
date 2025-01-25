package io.github.ger1211.auth_service.unit.service;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.builder.AccountBuilder;
import io.github.ger1211.auth_service.controller.vo.AccountVo;
import io.github.ger1211.auth_service.model.Account;
import io.github.ger1211.auth_service.model.Role;
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
        Account accountWithId = AccountBuilder.validCustomer(1L).build();

        when(authenticationRepository.save(any())).thenReturn(accountWithId);

        Account accountCreated = authenticationService.register(new AccountVo(accountWithId.getEmail(), accountWithId.getPassword()), Role.ROLE_CUSTOMER);

        assertNotNull(accountCreated);
        assertThat(accountCreated.getEmail()).isEqualTo(accountWithId.getEmail());
        assertNotNull(accountCreated.getId());
    }

    @Test
    void register_withValidUser_returnACustomerWithEncryptedPassword() throws Exception {
        String plainPassword = "PlainPassword123@";
        Account account = AccountBuilder.validCustomer().withPassword(plainPassword).build();

        Mockito.when(authenticationRepository.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Account accountCreated = authenticationService.register(new AccountVo(account.getEmail(), account.getPassword()), Role.ROLE_CUSTOMER);

        assertNotNull(accountCreated.getPassword());
        assertNotEquals(plainPassword, accountCreated.getPassword());
        assertTrue(passwordEncoder.matches(plainPassword, accountCreated.getPassword()));
    }
}
