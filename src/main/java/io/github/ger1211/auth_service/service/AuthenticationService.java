package io.github.ger1211.auth_service.service;

import io.github.ger1211.auth_service.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.ger1211.auth_service.model.Customer;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public Customer register(Customer customer) {
        return authenticationRepository.save(customer);
    }
}
