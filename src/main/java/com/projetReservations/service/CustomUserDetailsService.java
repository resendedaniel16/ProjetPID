package com.projetReservations.service;

import com.projetReservations.model.User;
import com.projetReservations.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Spring Security attend des rôles préfixés par ROLE_
        String role = "ROLE_" + user.getRole().name();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
