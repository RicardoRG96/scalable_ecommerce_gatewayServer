package com.ricardo.scalable.ecommerce.platform.gatewayServer.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ricardo.scalable.ecommerce.platform.gatewayServer.entities.User;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private WebClient.Builder client;

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        logger.info(username);

        try {
            User user = client.build()
                    .get()
                    .uri("/username/{username}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();

            logger.info(user.getUsername());
            logger.info(user.getPassword());

            List<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            
            logger.info(roles.get(0).getAuthority());
                
            return 
                new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEnabled(),
                    true,
                    true,
                    true,
                    roles
                );
        } catch (WebClientResponseException ex) {
            String error = "Error in login, the user '" + username + "' does not exist";
            throw new UsernameNotFoundException(error);
        }

    }

}
