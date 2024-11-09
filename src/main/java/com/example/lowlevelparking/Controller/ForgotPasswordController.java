package com.example.lowlevelparking.Controller;

import com.example.lowlevelparking.Services.EmailService;
//import com.example.lowlevelparking.Services.ForgotPasswordService;
import com.example.lowlevelparking.Services.ForgotPasswordService;
import com.example.lowlevelparking.dtos.passwordreset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/send-otp")
    public void sendOtp(@RequestBody String jsonInput) {

            ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonInput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String email = jsonNode.get("email").asText();
        System.out.println("Sending OTP to: " + email);  // Debug log
        forgotPasswordService.forgotPassword(email);



    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody passwordreset  passwordreset) {
        try {
            // Verify OTP and reset password
//            System.out.println(passwordreset.getEmail());

            System.out.println(passwordreset.getOtp() +"jijijjijij");
            System.out.println(passwordreset.getnewPassword());
            forgotPasswordService.verifyOtpResetPassword(passwordreset.getOtp(), passwordreset.getnewPassword());
            return ResponseEntity.ok("Password reset successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
