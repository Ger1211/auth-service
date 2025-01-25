package io.github.ger1211.auth_service.service;

import io.github.ger1211.auth_service.model.Account;
import io.github.ger1211.auth_service.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AuthenticationRepository authenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = authenticationRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(account.getRole().name())
        );

        return new User(account.getEmail(), account.getPassword(), authorities);
    }
}
