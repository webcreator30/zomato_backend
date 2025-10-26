package com.example.zomato.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.zomato.service.CustomUserDetailsService;
import com.example.zomato.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getServletPath();
        if (requestPath.startsWith("/send-otp") ||
                requestPath.startsWith("/verify-otp") || requestPath.startsWith("signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        // Step 1: Check header presence and format
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 2: Extract JWT token
        String token = authHeader.substring(7);

        // Step 3: Extract mobile from token
        String mobile = jwtService.extractMobile(token);

        if (mobile != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(mobile)
                    .password("")
                    .authorities("USER")
                    .build();

            // Verify JWT validity -> Our Custom JWT Filter
            if (jwtService.isTokenValid(token, userDetails)) {

                // Creating an authentication object (authToken) using Spring Security’s
                // standard token class.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                // Adds extra details to the authentication token from the HTTP request (like
                // remote address, session info).
                // Makes user context richer if needed for auditing or logging.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Stores the authentication object in Spring Security’s context for the current
                // request.
                // This marks the user as fully authenticated: now, the rest of the application
                // (controllers, services) recognize the user and can enforce permissions.​
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }

        filterChain.doFilter(request, response);
    }

}
