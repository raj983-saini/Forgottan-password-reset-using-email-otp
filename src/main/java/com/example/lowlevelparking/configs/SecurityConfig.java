package com.example.lowlevelparking.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define the authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/auth/signup","/api/auth/login" ,"/api/auth/send-otp","/api/auth/reset-password" ).permitAll() // Allow signup and login without authentication
                        .anyRequest().authenticated() // Protect all other endpoints
                )
                .sessionManagement().disable() // Disable session management (stateless)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() // Enable form-based login
                .loginPage("/login") // Custom login page (if needed)
                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard upon successful login
                .permitAll()
                .and()
                .rememberMe() // Enable "Remember Me" functionality
                .key("uniqueAndSecret") // Secret key for remember me
                .tokenValiditySeconds(86400) // 1 day validity
                .and()

                .logout() // Enable logout functionality
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();

        return http.build();
    }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")  // Apply CORS for all endpoints under /api/*
                    .allowedOrigins("http://localhost:5173") // Allow requests from this origin
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify allowed methods
                    .allowedHeaders("*")  // Allow all headers
                    .allowCredentials(true) // Allow cookies
                    .maxAge(3600); // Cache preflight request for 1 hour
        }


    }


