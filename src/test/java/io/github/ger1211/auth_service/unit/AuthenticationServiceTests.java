package io.github.ger1211.auth_service.unit;

import io.github.ger1211.auth_service.AuthServiceApplicationTests;
import io.github.ger1211.auth_service.builder.CustomerBuilder;
import io.github.ger1211.auth_service.model.Customer;
import io.github.ger1211.auth_service.repository.AuthenticationRepository;
import io.github.ger1211.auth_service.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTests extends AuthServiceApplicationTests {

    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void register_withValidUser_returnARegisteredUser() {
        Customer customer = CustomerBuilder.valid().build();
        Customer customerWithId = CustomerBuilder.valid(1L).build();

        when(authenticationRepository.save(customer)).thenReturn(customerWithId);

        Customer customerCreated = authenticationService.register(customer);

        assertNotNull(customerCreated);
        assertThat(customerCreated.getEmail()).isEqualTo(customer.getEmail());
        assertNotNull(customerCreated.getId());
    }
}
