package io.github.ger1211.auth_service.builder;

import io.github.ger1211.auth_service.model.Customer;
import io.github.ger1211.builder.builder.AbstractPersistenceBuilder;

public class CustomerBuilder extends AbstractPersistenceBuilder<Customer> {

    private CustomerBuilder() {
        this.instance = new Customer();
    }

    public static CustomerBuilder valid() {
        return new CustomerBuilder();
    }

    public static CustomerBuilder valid(Long id) {
        CustomerBuilder builder = new CustomerBuilder();
        builder.instance.setId(id);
        return builder;
    }
}
