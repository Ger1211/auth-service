package io.github.ger1211.auth_service.builder;

import io.github.ger1211.auth_service.model.Account;
import io.github.ger1211.builder.builder.AbstractPersistenceBuilder;

public class AccountBuilder extends AbstractPersistenceBuilder<Account> {

    private AccountBuilder() {
        this.instance = new Account();
    }

    public static AccountBuilder valid() {
        AccountBuilder builder = new AccountBuilder();
        builder.instance.setEmail("valid@mail.com");
        builder.instance.setPassword("Password123@");
        return builder;
    }

    public static AccountBuilder valid(Long id) {
        AccountBuilder builder = AccountBuilder.valid();
        builder.instance.setId(id);
        return builder;
    }

    public AccountBuilder withPassword(String password) {
        this.instance.setPassword(password);
        return this;
    }
}
