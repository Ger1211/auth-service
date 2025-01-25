package io.github.ger1211.auth_service.repository;

import io.github.ger1211.auth_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
}
