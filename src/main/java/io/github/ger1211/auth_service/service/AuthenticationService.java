package io.github.ger1211.auth_service.service;

import io.github.ger1211.auth_service.model.Customer;
import io.github.ger1211.auth_service.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;

    public Customer register(Customer customer) {
        return authenticationRepository.save(customer);
    }
}
