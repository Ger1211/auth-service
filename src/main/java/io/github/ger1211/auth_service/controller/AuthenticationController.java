package io.github.ger1211.auth_service.controller;

import io.github.ger1211.auth_service.controller.handler.ValidationHandler;
import io.github.ger1211.auth_service.controller.vo.CustomerVo;
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
    public ResponseEntity<?> register(@Valid @RequestBody CustomerVo customer, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ValidationHandler.handle(bindingResult) : ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody CustomerVo customerVo, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ValidationHandler.handle(bindingResult) : login(customerVo);
    }

    private ResponseEntity<?> login(CustomerVo customer) {
        try {
            JwtTokenDto token = authenticationService.login(customer);
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
