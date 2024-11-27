package com.ricardo.scalable.ecommerce.platform.gatewayServer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code) {
        Map<String, String> mapResponse = new HashMap<>();
        mapResponse.put("code", code);
        return mapResponse;
    }

    @PostMapping("/logout")
    public Map<String, String> logout() {
        return Collections.singletonMap("logout", "Ok");
    }

}
