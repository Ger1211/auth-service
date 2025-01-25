package io.github.ger1211.auth_service.service;

import io.github.ger1211.auth_service.controller.vo.AccountVo;
import io.github.ger1211.auth_service.model.Account;
import io.github.ger1211.auth_service.repository.AuthenticationRepository;
import io.github.ger1211.auth_service.service.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationRepository authenticationRepository;

    public Account register(AccountVo accountVo) throws Exception {
        String encodedPassword = passwordEncoder.encode(accountVo.getPassword());
        Account account = new Account(null, accountVo.getEmail(), encodedPassword);
        return authenticationRepository.save(account);
    }

    public JwtTokenDto login(AccountVo accountVo) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountVo.getEmail(), accountVo.getPassword()));
        String jwtToken = jwtService.generateToken(accountVo.getEmail());
        return new JwtTokenDto(jwtToken);
    }
}
