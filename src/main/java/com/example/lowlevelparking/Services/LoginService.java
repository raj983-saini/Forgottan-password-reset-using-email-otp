package com.example.lowlevelparking.Services;

import com.example.lowlevelparking.dtos.logindto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    public Authentication authenticate(logindto loginDto) {
        // Authenticate the user by email and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        // After authentication, load the user details and return the user
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getEmail());

        // At this point, the user is authenticated successfully
        return authentication;
    }
}
