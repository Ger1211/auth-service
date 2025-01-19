package io.github.ger1211.auth_service.repository;

import io.github.ger1211.auth_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<Customer, Long> {

}
