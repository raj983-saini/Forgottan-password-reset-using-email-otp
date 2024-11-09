package com.example.lowlevelparking.Controller;

import com.example.lowlevelparking.Services.UserService;
import com.example.lowlevelparking.dtos.Registerdto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody Registerdto registerUserDto , HttpServletResponse response) {
        try {
            userService.userRegister(registerUserDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
