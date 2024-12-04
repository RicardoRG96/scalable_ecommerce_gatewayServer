package com.ricardo.scalable.ecommerce.platform.gatewayServer.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;      
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz -> {
            authz
                .requestMatchers(HttpMethod.GET, "/authorized", "/logout")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/register")
                .permitAll()
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/users/{id}",
                        "/api/users/change-password/{id}"
                )
                .hasAnyRole("ADMIN", "USER", "SELLER")
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/users/{id}",
                        "/api/users/username/{username}",
                        "/api/users/email/{email}",
                        "/api/users"
                )
                .hasRole("ADMIN")
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/users/roles/{id}",
                        "/api/users/block/{id}",
                        "/api/users/unlock/{id}"
                )
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();
        })
        // .addFilter(new JwtAuthent)
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }

}
