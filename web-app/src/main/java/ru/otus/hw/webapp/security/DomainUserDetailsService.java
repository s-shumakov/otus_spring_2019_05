package ru.otus.hw.webapp.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.hw.webapp.repostory.UserRepository;

import java.util.Collections;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String lowercaseLogin = login.toLowerCase();
        return userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getLogin(), user.getPassword(),
                        user.getAuthorities().stream()
                                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                                .collect(Collectors.toList())))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }
}
