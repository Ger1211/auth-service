package io.github.ger1211.auth_service.controller.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerVo {

    @Email
    @NotEmpty
    private String email;
}
