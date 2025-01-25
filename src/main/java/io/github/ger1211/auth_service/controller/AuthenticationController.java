package io.github.ger1211.auth_service.controller;

import io.github.ger1211.auth_service.controller.handler.ValidationHandler;
import io.github.ger1211.auth_service.controller.vo.AccountVo;
import io.github.ger1211.auth_service.service.AuthenticationService;
import io.github.ger1211.auth_service.service.dto.JwtTokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody AccountVo customer, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ValidationHandler.handle(bindingResult) : register(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AccountVo accountVo, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ValidationHandler.handle(bindingResult) : login(accountVo);
    }

    private ResponseEntity<?> register(AccountVo customer) {
        try {
            authenticationService.register(customer);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<?> login(AccountVo customer) {
        try {
            JwtTokenDto token = authenticationService.login(customer);
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
