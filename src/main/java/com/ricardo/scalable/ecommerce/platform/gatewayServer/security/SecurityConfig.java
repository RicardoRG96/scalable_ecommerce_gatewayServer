package com.ricardo.scalable.ecommerce.platform.gatewayServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz -> {
            authz
                    .requestMatchers("/authorized", "/logout")
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
        }).csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CAMBIAR ESTA CONFIG CUANDO TENGA EL SERVIDOR DE AUTORIZACION
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        Customizer.withDefaults()
                ))
                .build();
    }

}
