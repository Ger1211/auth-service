package io.github.ger1211.auth_service.repository;

import io.github.ger1211.auth_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
