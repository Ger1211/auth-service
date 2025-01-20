package io.github.ger1211.auth_service.controller.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CustomerVo {

    @Email
    @NotEmpty
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
