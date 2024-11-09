package com.example.lowlevelparking.Controller;

import com.example.lowlevelparking.Services.LoginService;
import com.example.lowlevelparking.Services.JwtService;
import com.example.lowlevelparking.dtos.logindto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtService jwtService;

    // The default expiration time for JWT (e.g., 1 hour)


    // The expiration time when "Remember Me" is checked (e.g., 7 days)
    private static final long REMEMBER_ME_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 7 days
    private static final long SHORT_EXPIRATION_TIME = 60 * 60 * 1000; // 1 hour

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody logindto loginDto, HttpServletResponse response) {
        // Authenticate the user
        Authentication authentication = loginService.authenticate(loginDto);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Set the expiration time based on the "Remember Me" option
        long expirationTime = loginDto.isRemember() ? REMEMBER_ME_EXPIRATION_TIME : SHORT_EXPIRATION_TIME;

        // Generate a JWT token with the selected expiration time
        String jwtToken = jwtService.generateToken(userDetails, expirationTime);

//        // Set the JWT token in an HTTP-only cookie
        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        jwtCookie.setMaxAge((int) (expirationTime / 1000)); // Convert milliseconds to seconds
        jwtCookie.setHttpOnly(true); // Prevent JavaScript access for security
        jwtCookie.setPath("/"); // Available for the whole application
//
//        // Add the JWT cookie to the response
          response.addCookie(jwtCookie);

        // Return success response
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(HttpServletRequest request) {
        // Retrieve JWT token from cookies
        String jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        // If no token found, ask the user to log in
        if (jwtToken == null) {
            return ResponseEntity.status(401).body("Please log in first.");
        }

     //    Validate and parse the JWT token
        if (jwtService.isTokenValid(jwtToken)) {
            String username = jwtService.extractUsername(jwtToken);
            return ResponseEntity.ok("Welcome back, " + username);
        } else {
            return ResponseEntity.status(401).body("Invalid token. Please log in again.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Invalidate the JWT cookie by setting its max age to 0
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setMaxAge(0); // Delete the cookie by setting max age to 0
        jwtCookie.setPath("/"); // Ensure the path matches the one used when setting the cookie

        // Add the invalidated cookie to the response
        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Logged out successfully.");
    }
}
