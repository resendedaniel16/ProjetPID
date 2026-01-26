package com.projetReservations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Autorisations
                .authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()

                        // READ Artists : accessible à tous
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/artists/**").permitAll()

                        // WRITE Artists : réservé ADMIN
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/artists/**").hasRole("ADMIN").requestMatchers(org.springframework.http.HttpMethod.DELETE, "/artists/**").hasRole("ADMIN")

                        // Tout le reste nécessite login
                        .requestMatchers("/users/**").hasRole("ADMIN").anyRequest().authenticated())

                // Form login custom
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
                // Logout
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll())
                // CSRF activé par défaut (OK)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
