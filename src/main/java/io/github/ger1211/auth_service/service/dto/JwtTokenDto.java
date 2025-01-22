package io.github.ger1211.auth_service.service.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class JwtTokenDto {

    private String token;

    public String getToken() {
        return "Bearer " + this.token;
    }
}
