package com.ricardo.scalable.ecommerce.platform.gatewayServer.filters;

import jakarta.servlet.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GlobalFilter implements Filter, Ordered {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return 100;
    }
}