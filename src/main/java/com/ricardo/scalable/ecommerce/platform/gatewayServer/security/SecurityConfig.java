package com.ricardo.scalable.ecommerce.platform.gatewayServer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.ricardo.scalable.ecommerce.platform.gatewayServer.security.filter.JwtAuthenticationFilter;
import com.ricardo.scalable.ecommerce.platform.gatewayServer.security.filter.JwtValidationFilter;

@Configuration
public class SecurityConfig {

        @Autowired
        private AuthenticationConfiguration authenticationConfiguration;

        @Bean
        AuthenticationManager authenticationManager() throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

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
                                .requestMatchers(
                                        HttpMethod.GET,
                                         "/api/products", 
                                         "/api/products/{id}"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/products/name/{name}"
                                )
                                .hasAnyRole("ADMIN", "SELLER")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/products/product-sku/{sku}"
                                )
                                .hasAnyRole("ADMIN", "SELLER")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/products"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/{id}")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/{id}")
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/products/categories",
                                        "/api/products/categories/{id}",
                                        "/api/products/categories/name/{name}"
                                )
                                .hasAnyRole("ADMIN", "SELLER")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/products/categories"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/categories/{id}")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/categories/{id}")
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/products/brands",
                                        "/api/products/brands/{id}",
                                        "/api/products/brands/name/{name}"
                                )
                                .hasAnyRole("ADMIN", "SELLER")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/products/brands"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/products/brands/{id}")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/brands/{id}")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated();
                        })
                        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                        .addFilter(new JwtValidationFilter(authenticationManager()))
                        .csrf(csrf -> csrf.disable())
                        .cors(cors -> cors.disable())
                        .sessionManagement(session -> session
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .build();
        }

}
