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
                                // PRODUCTS SEARCH ROUTES
                                .requestMatchers(
                                        HttpMethod.GET, 
                                        "/api/products/search",
                                        "/api/products/brand",
                                        "/api/products/category",
                                        "/api/products/price"
                                )
                                .permitAll()

                                // USERS ROUTES
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

                                // ADDRESSES ROUTES
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/users/addresses/{id}",
                                        "/api/users/addresses/user/{userId}",
                                        "/api/users/addresses/user/{userId}/title/{title}",
                                        "/api/users/addresses/user/{userId}/addressLine1/{addressLine1}",
                                        "/api/users/addresses/user/{userId}/country/{country}",
                                        "/api/users/addresses/user/{userId}/city/{city}",
                                        "/api/users/addresses/user/{userId}/commune/{commune}",
                                        "/api/users/addresses/user/{userId}/postalCode/{postalCode}",
                                        "/api/users/addresses/user/{userId}/landmark/{landmark}"
                                )
                                .hasAnyRole("ADMIN", "USERS", "SELLER")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/users/addresses"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/users/addresses"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/users/addresses/{id}"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/users/addresses/{id}"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")

                                // WISHLIST ROUTES
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/users/wishlist/user/{userId}"
                                )
                                .hasAnyRole("ADMIN", "USER")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/users/wishlist/{id}",
                                        "/api/users/wishlist/product-sku/{productSkuId}",
                                        "/api/users/wishlist"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/users/wishlist"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/users/wishlist/{id}"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/users/wishlist/{id}"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")

                                // PRODUCTS ROUTES
                                .requestMatchers(
                                        HttpMethod.GET, 
                                        "/api/products/{id}",
                                        "/api/products/name/{name}"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")
                                .requestMatchers(
                                        HttpMethod.GET, 
                                        "/api/products"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.POST, 
                                        "/api/products"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.PUT, 
                                        "/api/products/{id}"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.DELETE, 
                                        "/api/products/{id}"
                                )
                                .hasRole("ADMIN")

                                // PRODUCT-SKU ROUTES
                                .requestMatchers(
                                        HttpMethod.GET, 
                                        "/api/products/product-sku/{id}"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/products/product-sku/product/{productId}",
                                        "/api/products/product-sku/sku/{sku}",
                                        "/api/products/product-sku/sku/{sku}/isActive/{isActive}",
                                        "/api/products/product-sku/sizeAttributeId/{sizeAttributeId}",
                                        "/api/products/product-sku/colorAttributeId/{colorAttributeId}",
                                        "/api/products/product-sku/productId/{productId}/sizeAttributeId/{sizeAttributeId}",
                                        "/api/products/product-sku/productId/{productId}/colorAttributeId/{colorAttributeId}",
                                        "/api/products/product-sku/productId/{productId}/sizeAttributeId/{sizeAttributeId}/colorAttributeId/{colorAttributeId}",
                                        "/api/products/product-sku"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/products/product-sku"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/products/product-sku/{id}"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/products/product-sku/{id}"
                                )
                                .hasRole("ADMIN")

                                // BRANDS ROUTES
                                .requestMatchers(
                                        HttpMethod.GET, 
                                        "/api/products/brands/{id}",
                                        "/api/products/brands/name/{name}"
                                )
                                .hasAnyRole("ADMIN", "USER", "SELLER")
                                .requestMatchers(
                                        HttpMethod.GET, 
                                        "/api/products/brands"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.POST, 
                                        "/api/products/brands"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.PUT, 
                                        "/api/products/brands/{id}"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.DELETE, 
                                        "/api/products/brands/{id}"
                                )
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
