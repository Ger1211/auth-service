package io.github.ger1211.auth_service.service;

import io.github.ger1211.auth_service.controller.vo.CustomerVo;
import io.github.ger1211.auth_service.model.Customer;
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

    public Customer register(CustomerVo customerVo) throws Exception {
        String encodedPassword = passwordEncoder.encode(customerVo.getPassword());
        Customer customer = new Customer(null, customerVo.getEmail(), encodedPassword);
        return authenticationRepository.save(customer);
    }

    public JwtTokenDto login(CustomerVo customerVo) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerVo.getEmail(), customerVo.getPassword()));
        String jwtToken = jwtService.generateToken(customerVo.getEmail());
        return new JwtTokenDto(jwtToken);
    }
}
