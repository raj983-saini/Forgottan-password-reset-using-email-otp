package com.example.lowlevelparking.Services;

import com.example.lowlevelparking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public void sendOtpEmail(String to, String subject, String otp) {
        // Validate the email address format
        if (to == null || !EMAIL_PATTERN.matcher(to).matches()) {
            throw new IllegalArgumentException("Invalid email address: " + to);
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText("Your OTP code is: " + otp);

            mailSender.send(message);
        } catch (MailParseException e) {
            // Log the error with detailed message
            System.err.println("MailParseException while sending email to " + to + ": " + e.getMessage());
            throw new MailParseException("Could not send email to " + to, e);
        } catch (Exception e) {
            // Handle any other exceptions
            e.printStackTrace();
            throw new RuntimeException("Could not send email to " + to, e);
        }
    }
}
