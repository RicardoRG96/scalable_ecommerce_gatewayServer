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
    SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz -> {
            authz
                    .requestMatchers("/authorized", "/logout")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users/register")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users/register")
                    .hasAnyRole()
                    .requestMatchers(
                            HttpMethod.GET,
                            "/api/users/{id}",
                            "/api/users/username/{username}",
                            "/api/users/email/{email}",
                            "/api/users"
                    )
                    .hasAnyRole("ADMIN", "SELLER")
                    .requestMatchers(
                            HttpMethod.PUT,
                            "/api/users/{id}",
                            "/api/users/change-password/{id}"
                    )
                    .hasAnyRole("ADMIN", "USER", "SELLER")
                    .requestMatchers(
                            HttpMethod.PUT,
                            "/api/users/roles/{id}",
                            "/api/users/block/{id}",
                            "/api/users/unlock/{id}"
                    )
                    .hasAnyRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/users/{id}")
                    .hasAnyRole("ADMIN")
                    .anyRequest()
                    .authenticated();
        }).cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CAMBIAR ESTA CONFIG CUANDO TENGA EL SERVIDOR DE AUTORIZACION
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(Customizer.withDefaults())
                .build();
    }

}
