package io.github.ger1211.auth_service.builder;

import io.github.ger1211.auth_service.model.Account;
import io.github.ger1211.auth_service.model.Role;
import io.github.ger1211.builder.builder.AbstractPersistenceBuilder;

public class AccountBuilder extends AbstractPersistenceBuilder<Account> {

    private AccountBuilder() {
        this.instance = new Account();
    }

    public static AccountBuilder validCustomer() {
        AccountBuilder builder = new AccountBuilder();
        builder.instance.setEmail("valid@mail.com");
        builder.instance.setPassword("Password123@");
        builder.instance.setRole(Role.ROLE_CUSTOMER);
        return builder;
    }

    public static AccountBuilder validCustomer(Long id) {
        AccountBuilder builder = AccountBuilder.validCustomer();
        builder.instance.setId(id);
        return builder;
    }

    public AccountBuilder withPassword(String password) {
        this.instance.setPassword(password);
        return this;
    }
}
