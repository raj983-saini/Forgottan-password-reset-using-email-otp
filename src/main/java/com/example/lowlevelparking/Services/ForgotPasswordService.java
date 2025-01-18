package com.example.lowlevelparking.Services;


import com.example.lowlevelparking.Entities.OtpToken;
import com.example.lowlevelparking.Repository.OtpTokenRepository;
import com.example.lowlevelparking.Repository.UserRepository;
import com.example.lowlevelparking.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Autowired
    private EmailService emailService;

    // Generate a random OTP
    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    // Send OTP to the user's email
    public void forgotPassword(String email) {
        // Verify user exists
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found with email: " + email);
        }

        // Generate and save/update OTP
        String otp = generateOtp();
        OtpToken otpToken = otpTokenRepository.findByEmail(email);

        if (otpToken == null) {
            otpToken = new OtpToken();
            otpToken.setEmail(email);
        }

        otpToken.setOtp(otp);
        otpToken.setCreatedAt(System.currentTimeMillis());
        otpTokenRepository.save(otpToken);

        // Send the OTP via email
        emailService.sendOtpEmail(email, "Password Reset OTP", otp);
    }

    // Verify OTP and reset the password
    public void verifyOtpResetPassword(String otp, String newPassword) {
        // Fetch and validate OTP
        OtpToken otpToken = otpTokenRepository.findByOtp(otp);

        if (otpToken == null || isOtpExpired(otpToken)) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // Reset the password
        userRepository.updatePasswordByEmail(passwordEncoder.encode(newPassword), otpToken.getEmail());

        // Remove OTP after successful password reset
        otpTokenRepository.delete(otpToken);
    }

    // Check if the OTP is expired
    private boolean isOtpExpired(OtpToken otpToken) {
        long expirationTimeInMillis = 5 * 60 * 1000; // 5 minutes
        return System.currentTimeMillis() - otpToken.getCreatedAt() > expirationTimeInMillis;
    }
}
