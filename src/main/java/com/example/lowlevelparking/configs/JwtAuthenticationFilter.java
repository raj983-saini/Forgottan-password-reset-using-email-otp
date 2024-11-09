package com.example.lowlevelparking.configs;

import com.example.lowlevelparking.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = null;
//
//        // Try to get JWT token from "Authorization" header (Bearer Token)
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }
//
//        // If JWT token is not in the Authorization header, try to get it from cookies
//        if (jwtToken == null) {
//            Cookie[] cookies = request.getCookies();
//            if (cookies != null) {
//                Optional<Cookie> jwtCookie = Arrays.stream(cookies)
//                        .filter(cookie -> "jwt".equals(cookie.getName()))
//                        .findFirst();
//                if (jwtCookie.isPresent()) {
//                    jwtToken = jwtCookie.get().getValue();
//                }
//            }
//        }
//
//        // Validate the token and authenticate
        if (jwtToken != null && jwtService.isTokenValid(jwtToken)) {
            String username = jwtService.extractUsername(jwtToken);

            // Fetch user details from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

//            // Set up the authentication context
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
//
//        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
