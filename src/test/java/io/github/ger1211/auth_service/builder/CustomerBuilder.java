package io.github.ger1211.auth_service.builder;

import io.github.ger1211.auth_service.model.Customer;
import io.github.ger1211.builder.builder.AbstractPersistenceBuilder;

public class CustomerBuilder extends AbstractPersistenceBuilder<Customer> {

    private CustomerBuilder() {
        this.instance = new Customer();
    }

    public static CustomerBuilder valid() {
        CustomerBuilder builder = new CustomerBuilder();
        builder.instance.setEmail("valid@mail.com");
        builder.instance.setPassword("Password123@");
        return builder;
    }

    public static CustomerBuilder valid(Long id) {
        CustomerBuilder builder = CustomerBuilder.valid();
        builder.instance.setId(id);
        return builder;
    }

    public CustomerBuilder withPassword(String password) {
        this.instance.setPassword(password);
        return this;
    }
}
