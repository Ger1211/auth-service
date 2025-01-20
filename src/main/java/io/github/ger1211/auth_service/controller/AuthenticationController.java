package io.github.ger1211.auth_service.controller;

import io.github.ger1211.auth_service.controller.handler.ValidationHandler;
import io.github.ger1211.auth_service.controller.vo.CustomerVo;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody CustomerVo customer, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ValidationHandler.handle(bindingResult) : ResponseEntity.ok().build();
    }

}
