package com.vergara.cashcard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/cashcards/**").authenticated()
                .requestMatchers("/h2-console/**").permitAll() 
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> httpBasic
                .and()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**") // NO CSRF
                .disable() // ENABLE NANI KUNG PRODUCTION SIYA
            )
            .headers(headers -> headers
                .frameOptions().sameOrigin() 
            );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("ivyvergara")
            .password("{noop}password123") 
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}